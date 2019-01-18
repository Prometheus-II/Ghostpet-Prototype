package ghostpet;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import ghost_behaviors.Behavior;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.regex.Pattern;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;

public class SpeakPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7710468722110870181L;

	/**
	 * Create the panel.
	 */
	JLabel SpeakLabel;
	JPanel AskPanel;
	GhostBase parent;
	
	public SpeakPanel(GhostBase p) {
		parent = p;
		
		setFont(parent.parent.data.font);
		
		setBorder(null);
		setBackground(new Color(0,0,0,0));
		
		setPreferredSize(new Dimension(200,300));
		setLayout(new BorderLayout(0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayout(null);
		
		JPanel main_panel = new JPanel();
		layeredPane.add(main_panel);
		main_panel.setBounds(0, 0, 200, 300);
		
		GridBagLayout gbl_layeredPane = new GridBagLayout();
		gbl_layeredPane.columnWidths = new int[]{0};
		gbl_layeredPane.rowHeights = new int[]{0};
		gbl_layeredPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_layeredPane.rowWeights = new double[]{Double.MIN_VALUE};
		main_panel.setLayout(gbl_layeredPane);
		main_panel.setBackground(new Color(0,0,0,0));
		
		SpeakLabel = new JLabel("SpeakLabel");
		if(getFont() != null)
			SpeakLabel.setFont(getFont());
		layeredPane.setLayer(SpeakLabel, 1);
		GridBagConstraints gbc_SpeakLabel = new GridBagConstraints();
		gbc_SpeakLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_SpeakLabel.insets = new Insets(15, 15, 5, 0);
		gbc_SpeakLabel.anchor = GridBagConstraints.PAGE_START;
		gbc_SpeakLabel.gridx = 0;
		gbc_SpeakLabel.gridy = 0;
		main_panel.add(SpeakLabel, gbc_SpeakLabel);
		
		AskPanel = new JPanel();
		layeredPane.setLayer(AskPanel, 1);
		AskPanel.setBackground(new Color(0,0,0,0));
		GridBagConstraints gbc_AskPanel = new GridBagConstraints();
		gbc_AskPanel.weighty = 1.0;
		gbc_AskPanel.anchor = GridBagConstraints.NORTH;
		gbc_AskPanel.fill = GridBagConstraints.BOTH;
		gbc_AskPanel.gridx = 0;
		gbc_AskPanel.gridy = 1;
		main_panel.add(AskPanel, gbc_AskPanel);
		
		GridBagLayout gbl_AskPanel = new GridBagLayout();
		gbl_AskPanel.columnWidths = new int[]{0, 0};
		gbl_AskPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_AskPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_AskPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		AskPanel.setLayout(gbl_AskPanel);
		
		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 7;
		AskPanel.add(verticalGlue, gbc_verticalGlue);
		
		JLabel BckgrndLbl = new JLabel();
		BckgrndLbl.setIcon(parent.parent.data.TextBox);
		layeredPane.setLayer(BckgrndLbl, 0);
		layeredPane.add(BckgrndLbl);
		BckgrndLbl.setBounds(0, 0, 200, 300);
		
		AskPanel.setVisible(false);
		setVisible(false);
	}
	
	public void Say(String state, String[] opts, Behavior[] behaviors)
	{
		String out = state;
		out = out.replaceAll(Pattern.quote("$NAME$"), parent.parent.data.UserName);
		out = out.replaceAll(Pattern.quote("$GNAME$"), parent.parent.data.ghostName);
		out = "<html><body style='width: 170px'>" + out;
		SpeakLabel.setText(out);
		JLabel optLabel;
		GridBagConstraints gbc_optLabel;
		int x = 0, y = 0;
		for(int i = 0; i < opts.length; i++)
		{
			gbc_optLabel = new GridBagConstraints();
			gbc_optLabel.gridx = x;
			gbc_optLabel.gridy = y;
			gbc_optLabel.weighty = 0;
			gbc_optLabel.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc_optLabel.ipady = 10;
			gbc_optLabel.insets = new Insets(0, 15, 5, 0);
			out = opts[i];
			out = out.replaceAll(Pattern.quote("$NAME$"), parent.parent.data.UserName);
			out = out.replaceAll(Pattern.quote("$GNAME$"), parent.parent.data.ghostName);
			out = "<html><body style='width: 170px'>" + out;
			optLabel = new AskChoiceLabel<Behavior>("* "+out, behaviors[i]);
			optLabel.addMouseListener(new MouseUser(parent.parent));
			if(getFont() != null)
				optLabel.setFont(getFont());
			AskPanel.add(optLabel, gbc_optLabel);
			
			y++;
			if(y == 6)
			{
				y=0;
				x++;
			}
		}
		AskPanel.setVisible(true);
		setVisible(true);
		updateUI();
	}
	public void Say(String state)
	{
		AskPanel.setVisible(false);
		SpeakLabel.setText(state);
		setVisible(true);
		updateUI();
	}
	
	public void Reset()
	{
		AskPanel.setVisible(false);
		setVisible(false);
		updateUI();
	}
}
