package ghostpet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ghost_behaviors.Behavior_Ask;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class GhostBase extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 557122829397947123L;
	
	GhostPanel gpanel;
	SpeakPanel spanel;
	GhostFrame parent;
	
	/**
	 * Create the panel.
	 */
	public GhostBase(GhostFrame frame) {
		parent = frame;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		spanel = new SpeakPanel(this);
		GridBagConstraints gbc_spanel = new GridBagConstraints();
		gbc_spanel.weightx = 1.0;
		gbc_spanel.weighty = 1.0;
		gbc_spanel.insets = new Insets(0, 0, 0, 5);
		gbc_spanel.fill = GridBagConstraints.BOTH;
		gbc_spanel.gridx = 0;
		gbc_spanel.gridy = 0;
		gbc_spanel.anchor = GridBagConstraints.NORTHWEST;
		add(spanel, gbc_spanel);
		
		gpanel = new GhostPanel(frame.data.defImg, parent);
		GridBagConstraints gbc_gpanel = new GridBagConstraints();
		gbc_gpanel.gridx = 1;
		gbc_gpanel.gridy = 0;
		gbc_gpanel.anchor = GridBagConstraints.SOUTHEAST;
		add(gpanel, gbc_gpanel);

		setBackground(new Color(0,0,0,0));
		this.setMinimumSize(new Dimension(gpanel.getWidth()+200, gpanel.getHeight()));
		setPreferredSize(new Dimension(gpanel.getWidth()+200, spanel.getHeight()));
		updateUI();
	}
	
	public void AskSay(Behavior_Ask b)
	{
		spanel.Say(b.getRandStmt(), b.getActStatements(), b.associatedBehaviors);
		gpanel.Say(b.getImg());
		parent.repaint();
	}
	
	public void Say(String state, ImageIcon in)
	{
		spanel.Say(state);		
		gpanel.Say(in);
		parent.repaint();
	}
	public void Reset()
	{
		spanel.Reset();
		gpanel.Reset();
		parent.state = GhostState.Passive;
		parent.repaint();
	}
}
