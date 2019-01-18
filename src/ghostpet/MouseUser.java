package ghostpet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ghost_behaviors.Behavior;
import ghost_behaviors.PetBehavior;

public class MouseUser extends MouseAdapter {
	GhostFrame parent;
	
	public MouseUser() {
		// TODO Auto-generated constructor stub
	}
	
	public MouseUser(GhostFrame p)
	{
		parent = p;
	}
	
	@SuppressWarnings("unchecked") //Potentially bad practice, but it DOES get checked in the if() statement. 
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource() instanceof AskChoiceLabel)
		{
			parent.ghost.Reset();
			parent.Say(parent.getBehavior(((AskChoiceLabel<Behavior>) e.getSource()).cls));
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(e.getSource() == parent.ghost.gpanel.CharLabel) //Probably don't need to be this finicky, but it's a safety precaution.
			parent.getBehavior(PetBehavior.class).Execute();
		
		
	}
}
