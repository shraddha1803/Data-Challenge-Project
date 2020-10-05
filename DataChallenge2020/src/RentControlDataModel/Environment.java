package RentControlDataModel;

import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;

public class Environment extends SimState {
	public SparseGrid2D space;
	public ArrayList<Home> homeSpace;
	public int gridWidth = 67;
	public int gridHeight = 85; 
	public int n = 5695; // 67 * 85 = 5695, estimated total number of rental households in California in 2018, scaled down by one thousandth (.001) for the model
	public boolean oneAgentperCell = true;
	boolean localRentControl = false; // whether or not local rent control is enacted
	boolean vacancyControl = false; // whether or not vacancy control is enacted, should be false if localRentControl is false
	GUIsim gui = null;
	int moves = 0; // total number of moves by end
	
	int numIncomeLevel1 = 0;
	int numIncomeLevel2 = 0;
	int numIncomeLevel3 = 0;
	int numIncomeLevel4 = 0;
	int numIncomeLevel5 = 0;
	int numIncomeLevel6 = 0;
	int numIncomeLevel7 = 0;
	int numIncomeLevel8 = 0;
	int numIncomeLevel9 = 0;
	int numIncomeLevel10 = 0;
	
	public Environment(long seed) {
		super(seed);
	}
	
	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public boolean isOneAgentperCell() {
		return oneAgentperCell;
	}

	public void setOneAgentperCell(boolean oneAgentperCell) {
		this.oneAgentperCell = oneAgentperCell;
	}

	public void makeAgents() {
		int t = 0;
		double [] incomes = populateIncomes(); // populate incomes used for tenants
		for(int i=0;i<n;i++) {
			int x = random.nextInt(gridWidth); // find an x location
			int y = random.nextInt(gridHeight); // find a y location
			Object z = space.getObjectsAtLocation(x, y); // get tenant living at this location
			Home v = new Home();
			for (Home h : homeSpace) {
				if (h.getX() == x && h.getY() == y) {
					v = h; // get home at this location
					break;
				}
			}
			int k = 0;
			boolean found = true;
			while (oneAgentperCell && z != null && (v.getRent() > (incomes[t] * Agent.percent))) { // if not empty or
				x = random.nextInt(gridWidth);                                                     // rent of home > income of next tenant
				y = random.nextInt(gridHeight);                                                    // get next location until one works
				z = space.getObjectsAtLocation(x, y);
				for (Home h : homeSpace) {
					if (h.getX() == x && h.getY() == y) {
						v = h; // get home at this location
						break;
					}
				}
				if (k == (gridWidth * gridHeight)) {
					found = false;
					break;
				}
				k++;
			}
			
			if (found) {
				Agent a = new Agent(x, y, incomes[t]); // make a tenant with verified info
				a.colorByIncome(a.income, this, a);
				a.setIncomeForRent();
				space.setObjectLocation(a, x, y); // set tenant in environment
				a.event = schedule.scheduleRepeating(a); // schedule tenant to update each step
			}
			t++; // update to next income in list
		}
	}
	
	public double [] populateRents() {
		double[] rents = new double[gridWidth * gridHeight]; // fill with monthly rent prices, scaled using data from 2018 ACS 5-Year Estimates Data Profiles
		Random r = new Random();
		
		int low = 0;
		int high = 499;
		int total = 0;
		
		for(int i = total; i < total + 288; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 500;
		high = 999;
		total += 288;
		for(int i = total; i < total + 1067 ; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 1000;
		high = 1499;
		total += 1067;
		for(int i = total; i < total + 1729; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 1500;
		high = 1999;
		total += 1729;
		for(int i = total; i < total + 1273; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 2000;
		high = 2499;
		total += 1273;
		for(int i = total; i < total + 692; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 2500;
		high = 2999;
		total += 692;
		for(int i = total; i < total + 337; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		
		low = 3000;
		high = 10000;
		total += 337;
		for(int i = total; i < total + 309; i++){
			rents[i]  = r.nextInt(high-low) + low;
		}
		total += 309;
		return rents;
	}
	
	public double [] populateIncomes() {
		double[] incomes = new double[n]; // fill with random yearly incomes within range taken from from 2018 ACS 5-Year Estimates Data Profiles
		Random r = new Random();
		int Low = 10000;
		int High = 250000;

		for(int i = 0; i < n; i++){
		        incomes[i]  = r.nextInt(High-Low) + Low;
		    }
		return incomes;
	}
	
	public void start() {
		super.start(); // starts the simulation
		space = new SparseGrid2D(gridWidth, gridHeight); // initialize grid space for tenants
		homeSpace = new ArrayList<Home>(); // initialize array list for homes
		double rents [] = populateRents(); // populate rent array
		int t = 0;
		for (int xcoor = 0; xcoor < gridWidth; xcoor++) {
			for (int ycoor = 0; ycoor < gridHeight; ycoor++) {
				Home h = new Home(xcoor, ycoor); // fill array list with homes
				h.setRent(rents[new Random().nextInt(rents.length)]); // initialize rents with random values from rents array
				homeSpace.add(h);
				t++;
			}
		}
		makeAgents(); // fill grid with tenants and put tenants in homes
		AfterStep printOut = new AfterStep(); 
		schedule.scheduleRepeating(printOut); // print out important information to Excel worksheet each step
	}
}
