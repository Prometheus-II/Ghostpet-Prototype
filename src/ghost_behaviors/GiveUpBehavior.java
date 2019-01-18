package ghost_behaviors;

import ghostpet.GhostFrame;
import ghostpet.GhostState;

public class GiveUpBehavior extends Behavior {

	//NOTE: Use this class for any Ask behavior as the "cancel" or "never mind" option, and for when the ask "times out."
	
	public GiveUpBehavior(GhostFrame frame) {
		super(frame);
		// TODO Auto-generated constructor stub
	}

	public GiveUpBehavior() {
		entersState = GhostState.Speaking;
	}

	@Override
	public void Execute() {
		// TODO Auto-generated method stub
		parent.Say(this);
	}
	/*
	 * Executes when: the user either times out the ask or selects a "never mind" option 
	 */
}
