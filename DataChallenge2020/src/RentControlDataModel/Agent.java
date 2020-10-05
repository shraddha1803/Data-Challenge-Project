package RentControlDataModel;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public class Agent implements Steppable {
	public Stoppable event;
	int x; // x coordinate tenant lives at
	int y; // y coordinate tenant lives at
	double income; // yearly income of the tenant
	int incomeLevel; // income bracket of tenant from 1(lowest)-10(highest)
	static double percent = .30; // percent to calculate income used for rent, suggested 30% of total income
	double percentChange = .02492105784; // average percent change in income from year to year in California, calculated using census data in Excel file: Average Percent Change in Income from Year to Year In California.xlsx
	double incomeForRent; // amount of monthly income used for rent
	int numYearsInHome = 0; // number of years (steps) tenant lived in current home
	
	public Agent(int x, int y, double income) {
		super();
		this.x = x;
		this.y = y;
		this.income = income;
	}
	
	public void move(SimState state) {
		updateIncome(this); // update income by average yearly percent change
		this.setIncomeForRent(); // update amount of income used for rent
		Environment environment = (Environment)state;
		Home v = new Home();
		for (Home h : environment.homeSpace) {
			if (h.getX() == this.x && h.getY() == this.y) {
				v = h; // get home at this location
				break;
			}
		}
		v.updateRentNoMove(environment, this.numYearsInHome); // update rent by yearly change, differs if rent control is true and for 3 years after tenant moves
		if (this.incomeForRent < v.rent) {// check if income percentage is less than rent of current home
			// if rent is too expensive...
			placeAgent(environment); // places tenant in new home if available
		}
		else this.numYearsInHome++; // else don't move and stay in home another year
	}
	
	public void placeAgent(Environment state) {
		if(state.oneAgentperCell) {
			boolean moved = false;
			int tempx = x;
			int tempy = y;
			
			for (Home h : state.homeSpace) { // look through homes for a place to move
				if ((h.getRent() <= this.incomeForRent) && h.getAgent() == null) { // if rent of home is less than income for rent
					tempx = h.getX();                                              // and there is no tenant already, then move
					tempy = h.getY();
					h.setAgent(this); // set tenant in home
					moved = true; // flag that a move was completed
					state.moves++; // add to total number of moves
					if (!state.vacancyControl) {
						h.updateRentMove(state, this.numYearsInHome); // update rent after tenant has moved
					}
					this.numYearsInHome = 0;
					break;
				}
			}
			
			if(moved == false) {
				state.space.remove(this); // if a home is not found, remove tenant from environment
				switch (this.incomeLevel) {
					case 1:
						state.numIncomeLevel1--;
						break;
					case 2:
						state.numIncomeLevel2--;
						break;
					case 3:
						state.numIncomeLevel3--;
						break;
					case 4:
						state.numIncomeLevel4--;
						break;
					case 5:
						state.numIncomeLevel5--;
						break;
					case 6:
						state.numIncomeLevel6--;
						break;
					case 7:
						state.numIncomeLevel7--;
						break;
					case 8:
						state.numIncomeLevel8--;
						break;
					case 9:
						state.numIncomeLevel9--;
						break;
					case 10:
						state.numIncomeLevel10--;
						break;
				}
				event.stop();
			}
			else {
				x = tempx;
				y = tempy;
				state.space.setObjectLocation(this, x, y); // if a home is found, then move tenant to the new home
			}
		}
	}

	public void updateIncome(Agent a) {
		a.income = a.income + (a.income * percentChange); // current income multiplied by average percent change in income every year
	}
	
	public void setIncomeForRent() {
		this.incomeForRent = (this.income/12) * percent; // amount of income used for rent per month, suggested to be 30% of total income
	}
	
	public void colorByIncome(double income, Environment state, Agent a) { // assigns tenants colors by income level and adds them to total nums
		if (income <= 10000) {
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0, (float)1, (float)1);
			this.incomeLevel = 1;
			state.numIncomeLevel1++;
		}
		else if (income <= 14999) {
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0, (float)0, (float)1);
			this.incomeLevel = 2;
			state.numIncomeLevel2++;
		}
		else if (income <= 24999) {
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)1, (float)0, (float)1);
			this.incomeLevel = 3;
			state.numIncomeLevel3++;
		}
		else if (income <= 34999) {
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0, (float)1, (float)1);
			this.incomeLevel = 4;
			state.numIncomeLevel4++;
		}
		else if (income <= 49999) {
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)1, (float)1, (float)1);
			this.incomeLevel = 5;
			state.numIncomeLevel5++;
		}
		else if (income <= 74999) {
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0.6, (float)0, (float)1);
			this.incomeLevel = 6;
			state.numIncomeLevel6++;
		}
		else if (income <= 99999) {
			state.gui.setOvalPortrayal2DColor(a, (float).1, (float).03, (float)1, (float)1);
			this.incomeLevel = 7;
			state.numIncomeLevel7++;
		}
		else if (income <= 149999) {
			state.gui.setOvalPortrayal2DColor(a, (float)0, (float)0, (float)0, (float)1);
			this.incomeLevel = 8;
			state.numIncomeLevel8++;
		}
		else if (income <= 199999) {
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)1, (float).5, (float)1);
			this.incomeLevel = 9;
			state.numIncomeLevel9++;
		}
		else if (income >= 200000) {
			state.gui.setOvalPortrayal2DColor(a, (float)1, (float)0.5, (float)1, (float)1);
			this.incomeLevel = 10;
			state.numIncomeLevel10++;
		}
	}
	
	public void step(SimState state) {
		move(state);
	}

}
