package com.coalitionbuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class UtilityGUI implements ActionListener {
	private Coalition coalition;
	
	private JTextArea partyDetails;
	private JScrollPane scrollPane;
	private JTextArea coalitionDetails;
	private JScrollPane scrollPane2;
	private JButton saveCoalition;

	public UtilityGUI() {
		JFrame frame = new JFrame();

		frame.setTitle("Utility GUI");
		frame.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		////////////////////////////////////////////////////////////////////////////////
		JPanel partyPanel = new JPanel();

		partyPanel.setBackground(Color.LIGHT_GRAY);

		Border infoBorder = BorderFactory.createTitledBorder("PARTY DETAILS");
		partyPanel.setBorder(infoBorder);

		partyDetails = new JTextArea();

		partyDetails.setPreferredSize(new Dimension(200, 200));
		partyDetails.setLineWrap(true);
		partyDetails.setWrapStyleWord(true);

		scrollPane = new JScrollPane(partyDetails);
		partyPanel.add(scrollPane);
		////////////////////////////////////////////////////////////////////////////////
		JPanel coalitionPanel = new JPanel();

		coalitionPanel.setBackground(Color.LIGHT_GRAY);

		Border coalitionBorder = BorderFactory.createTitledBorder("COALITION DETAILS");
		coalitionPanel.setBorder(coalitionBorder);

		coalitionDetails = new JTextArea();

		coalitionDetails.setPreferredSize(new Dimension(200, 200));
		coalitionDetails.setLineWrap(true);
		coalitionDetails.setWrapStyleWord(true);

		scrollPane2 = new JScrollPane(coalitionDetails);
		coalitionPanel.add(scrollPane2);
		////////////////////////////////////////////////////////////////////////////////
		JPanel buttonsPanel = new JPanel();
		
		buttonsPanel.setBackground(Color.LIGHT_GRAY);
		
		Border buttonsBorder = BorderFactory.createTitledBorder("ACTIONS");
		buttonsPanel.setBorder(buttonsBorder);
		
		saveCoalition = new JButton("Save Coalition");
		saveCoalition.addActionListener(this);
		
		buttonsPanel.add(saveCoalition);

		panel.add(partyPanel, BorderLayout.NORTH);
		panel.add(coalitionPanel, BorderLayout.CENTER);
		panel.add(buttonsPanel, BorderLayout.SOUTH);

		frame.add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == saveCoalition) {
			saveCoalition();
		}
	}

	public void setPartyDetails(Party party) {
		partyDetails.setText(party.getDetails());
	}

	public void setCoalitionDetails(Coalition coalition) {
		this.coalition = coalition;
		
		coalitionDetails.setText(coalition.getDetails());
	}

	public void setCoalitionStatus(int totalSeats, int coalitionSeats) {
		int majority = (totalSeats / 2) + 1;

		if (coalitionSeats >= majority) {
			// Coalition has a majority
			coalitionDetails.setText("Your coalition has a majority.");
		} else {
			coalitionDetails.setText("Your coalition does not have a majority.");
		}
	}
	
	public void saveCoalition() {
		if (coalition != null) {
			coalition.saveCoalition();
		} else {
			System.out.println("There is no coalition.");
		}
	}
}
