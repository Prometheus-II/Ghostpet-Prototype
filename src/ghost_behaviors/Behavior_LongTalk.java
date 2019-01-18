package ghost_behaviors;

import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;

import ghostpet.GhostFrame;
import ghostpet.GhostState;

public abstract class Behavior_LongTalk extends Behavior {
	
	public String[][] stmtChains;
	public int[][] timeChains;
	String[][] imgChains;
	
	public transient ImageIcon[][] imgsForChain;
	public transient int inc = 0;
	public transient int numUsed;
	
	public Behavior_LongTalk(GhostFrame frame) {
		super(frame);
		// TODO Auto-generated constructor stub
	}

	public Behavior_LongTalk() {
		entersState = GhostState.Speaking;
	}
	
	public void setup()
	{
		Random r = new Random();
		numUsed = r.nextInt(stmtChains.length);
		
		imgsForChain = new ImageIcon[imgChains.length][];
		for(int h = 0; h < imgChains.length; h++)
		{
			imgsForChain[h] = new ImageIcon[imgChains[h].length];
		}
		
		ImageIcon prev = null;
		for(int i = 0; i < imgChains.length; i++)
		{
			for(int j = 0; j < imgChains[i].length; j++)
			{
				if(imgChains[i][j] == null)
				{
					if(j == 0)
						imgsForChain[i][j] = parent.data.defImg;
					else
						imgsForChain[i][j] = prev;
				}
				else
				{ 
					imgsForChain[i][j] = new ImageIcon("./resources/images/"+imgChains[i][j]);
					//imgsForChain[i][j] = new ImageIcon(Behavior_LongTalk.class.getResource("/images/"+imgChains[i][j]));
				}
				prev = imgsForChain[i][j];
			}
		}
	}
		
	@Override
	public abstract void Execute();

	@Override
	public <T extends Behavior> T loadInfo(GhostFrame frame) {
		// TODO Auto-generated method stub
		
		T returner = super.loadInfo(frame);
		((Behavior_LongTalk)returner).setup();
		return returner;
	}
	
	
	
}
