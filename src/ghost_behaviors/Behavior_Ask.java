package ghost_behaviors;

import ghostpet.GhostFrame;
import ghostpet.GhostState;

public abstract class Behavior_Ask extends Behavior {
	private String[] actStatements;
	
	transient public Behavior[] associatedBehaviors;
	public String[] BehaviorList;
	
	public Behavior_Ask(GhostFrame frame) {
		super(frame);
		// TODO Auto-generated constructor stub
	}

	public Behavior_Ask() {
		entersState = GhostState.Asking;
	}
	
	public <T extends Behavior> void setup()
	{
		associatedBehaviors = new Behavior[BehaviorList.length];
		String str;
		for(int i = 0; i < BehaviorList.length; i++)
		{
			if(BehaviorList[i] == null)
			{
				associatedBehaviors[i] = null;
				continue;
			}
			str = "ghost_behaviors."+BehaviorList[i];
			
			try {
				@SuppressWarnings("unchecked")
				Class<T> C = (Class<T>) Class.forName(str);
				associatedBehaviors[i] = C.newInstance().loadInfo(parent);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	@Override
	public abstract void Execute();
	
	@Override
	public <T extends Behavior> T loadInfo(GhostFrame frame) {
		// TODO Auto-generated method stub
		T returner = super.loadInfo(frame);
		((Behavior_Ask) returner).setup();
		return returner;
	}

	public String[] getActStatements() {
		return actStatements;
	}

	public void setActStatements(String[] actStatements) {
		this.actStatements = actStatements;
	}
}
