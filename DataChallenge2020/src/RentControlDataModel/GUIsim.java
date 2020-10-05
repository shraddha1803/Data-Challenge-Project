package RentControlDataModel;

import java.awt.Color;
import sim.engine.SimState;
import sweep.GUISimState;

public class GUIsim extends GUISimState {

	public GUIsim(SimState state, String spaceName, int gridWidth, int gridHeight, Color backdrop,
			Color agentDefaultColor, boolean agentPortrayal) {
		super(state, spaceName, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		
	}
	
	public static void main(String[] args) {
		Environment s = (Environment)GUIsim.initialize(Environment.class, GUIsim.class, 400, 400, Color.white, Color.blue, false, "space");
		s.gui = (GUIsim)gui;
	}

}
