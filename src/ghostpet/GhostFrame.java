package ghostpet;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

import javax.swing.*;
import ghost_behaviors.*;

public class GhostFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4742194505986766707L;

	public GhostBase ghost;
	
	public GhostState state;
	public GhostJson data;
	
	Timer InnerClock;
	 
	ArrayList<Behavior> behaviorList;
	
	public <T extends Behavior> void MakeBehaviors()
	{
		File resFolder = new File("resources");
		File[] resourceList = resFolder.listFiles();
		behaviorList = new ArrayList<Behavior>(resourceList.length); //Definitely longer than needed, but also definitely not SHORTER than needed, and this way the number doesn't need to be changed.
		String jsonFileName;
		T behave;
		for(int i = 0; i < resourceList.length; i++)
		{
			jsonFileName = resourceList[i].getName();
			
			if(jsonFileName.indexOf("Behavior") == -1) //If "Behavior" isn't in there, it's not a behavior data file, and can thus be discounted.
				continue;
			
			jsonFileName = jsonFileName.substring(0, jsonFileName.length()-5); //Removes ".json"
			jsonFileName = "ghost_behaviors."+jsonFileName;
			
			try {
				@SuppressWarnings("unchecked") //Bad practice, but I've manually ensured that EVERY .json Behavior file can be translated to the class name with the above code.
				Class<T> C = (Class<T>) Class.forName(jsonFileName);
				
				behave = C.newInstance();
				behave = behave.loadInfo(this);
				behaviorList.add(behave);
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	public GhostFrame()
	{
		data = GhostJson.buildGson();  
		
		InnerClock = new Timer(60, null);
		InnerClock.setRepeats(false);
		
		MakeBehaviors();
		
		state = GhostState.Passive;
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle rect = ge.getMaximumWindowBounds();
		int x = (int) rect.getMaxX() - getWidth();
        int y = (int) rect.getMaxY() - getHeight();
        setLocation(x, y);
        getContentPane().setLayout(null);
        
        ghost = new GhostBase(this);
        ghost.setBounds(97, 0, this.getWidth()-97, this.getHeight()); //TODO: remove the "-97" once I remove the testing buttons
        getContentPane().add(ghost);
        
        JButton btnOff = new JButton("off");
        btnOff.setBounds(0, 0, 97, 25);
        btnOff.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent arg0)
        	{
        		System.exit(0);
        	}
        });
        getContentPane().add(btnOff);
        
        JButton btnTest = new JButton("test");
        btnTest.setBounds(0, 30, 97, 25);
        btnTest.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent arg0)
        	{
        		Say(getBehavior(GenLongTalkBehavior.class));
        	}
        });
        getContentPane().add(btnTest);
        
	}
	
	public <T extends Behavior> void Say(T b)
	{
		InnerClock = new Timer(60, null); //Resets the timer to clear out all action listeners
		state = b.entersState;
		
		if(Behavior_Ask.class.isAssignableFrom(b.getClass()))
		{
			ghost.AskSay((Behavior_Ask) b);
			InnerClock.setInitialDelay(b.actTime*1000);
			InnerClock.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					InnerClock.stop();
					ghost.Reset();
					getBehavior(GiveUpBehavior.class).Execute();
				}
			});
			InnerClock.start();
		}
		else if(Behavior_LongTalk.class.isAssignableFrom(b.getClass()))
		{
			Behavior_LongTalk br = (Behavior_LongTalk) b;
			if(br.inc < br.stmtChains[br.numUsed].length)
			{
				ghost.Say(br.stmtChains[br.numUsed][br.inc], br.imgsForChain[br.numUsed][br.inc]);
				InnerClock.setInitialDelay(br.timeChains[br.numUsed][br.inc] * 1000);
				InnerClock.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ghost.Reset();
						InnerClock.stop();
						Say(b);
					}
				});
				InnerClock.start();
				br.inc++;
			}
			else
			{
				br.inc = 0;
				Random r = new Random();
				br.numUsed = r.nextInt(br.stmtChains.length);
			}
		}
 		else
		{
			ghost.Say(b.getRandStmt(), b.getImg());
			InnerClock.setInitialDelay(b.actTime*1000);
			InnerClock.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ghost.Reset();
					InnerClock.stop();
				}
			});
			InnerClock.start();
		}
	}
	
	@SuppressWarnings("unchecked") //Yes, it's bad practice, but in this case it works. The cast from Behavior to T DOES get checked in the if statement before the return.
	public <T extends Behavior> T getBehavior(Class<T> cls)
	{
		for(int i = 0; i < behaviorList.size(); i++)
		{
			if(behaviorList.get(i).getClass().isAssignableFrom(cls))
			{
				return (T) behaviorList.get(i);
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		GhostFrame frame = new GhostFrame();
            		frame.setVisible(true);
            	} catch(Exception e){
            		e.printStackTrace();
            	}
            }
        });
    }
}
