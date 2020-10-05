package RentControlDataModel;

//import sim.engine.SimState;
//import sim.engine.Steppable;

public class Home {
	Agent a;
	int x;
	int y;
	double rent;
	double rentIncreaseStatewide = .05;
	double rentIncreaseAfterMove = .05;
	double rentIncreaseWithLocalControl = .018;
	double rentIncreaseToMarketValue = .03;
	
	public Home() {
	}
	
	public Home(int x, int y) {
		this.a = null;
		this.x = x;
		this.y = y;
	}
	
	public Home(Agent a, int x, int y) {
		this.a = a;
		this.x = x;
		this.y = y;
	}
	
	public Home(Agent a, int x, int y, double rent) {
		this.a = a;
		this.x = x;
		this.y = y;
		this.rent = rent;
	}
	
	public Home(Home h) {
		this.a = h.a;
		this.x = h.x;
		this.y = h.y;
		this.rent = h.rent;
	}
	
	public Agent getAgent() {
		return a;
	}
	
	public void setAgent(Agent a) {
		this.a = a;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getRent() {
		return rent;
	}
	
	public void setRent(double rent) {
		this.rent = rent;
	}
	
	public void updateRentNoMove(Environment state, int yearsNotMoved) {
		if (yearsNotMoved == 0 || yearsNotMoved == 1 || yearsNotMoved == 2 && state.vacancyControl) { // if tenant has moved within 3 years and vacancy control is true, update rent by 5% according to 2020 Prop 21 Ballot Initiative
			rent = rent + (rent * rentIncreaseAfterMove);
		}
		else {
			if (state.localRentControl == true) {
				rent = rent + (rent * rentIncreaseWithLocalControl); // increase rent by most recent allowable annual rent increase in San Francisco from file: 571 Allowable Annual Increases 20-21 EN 4.6.20.pdf
			}
			else {
				rent = rent + (rent * rentIncreaseStatewide); // else if no local rent control, raise by statewide rent increase cap value
			}
		}
	}
	
	public void updateRentMove(Environment state, int yearsNotMoved) {
		for (int i = 0; i < yearsNotMoved; i++) {
			rent = rent + (rent * rentIncreaseToMarketValue); // else increase to current market value by multiplying rent by average value increase for number of years tenant lived in home
		}                                                     // this rent change only happens once, right after tenant moves out
	}
}
