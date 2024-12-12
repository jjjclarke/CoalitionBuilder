package com.coalitionbuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TableGUI implements ActionListener, ListSelectionListener {
	private UtilityGUI utilityGUI;
	private Coalition coalition;
	
	private JButton loadResults;
	private JComboBox<String> comboBox;
	private boolean nationalElection; // True if national, false if local.
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private JLabel status;
	private JButton addParty;
	private JButton removeParty;
	private JButton buildCoalition;
	public TableGUI() {
		JFrame frame = new JFrame();
		
		frame.setTitle("Table GUI");
		frame.setLayout(new BorderLayout());
		
		coalition = new Coalition();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		////////////////////////////////////////////////////////////////////////////////
		JPanel loaderPanel = new JPanel();
		
		loaderPanel.setBackground(Color.LIGHT_GRAY);
		
		Border loaderBorder = BorderFactory.createTitledBorder("LOAD RESULTS");
		loaderPanel.setBorder(loaderBorder);
		
		loadResults = new JButton("Load Election Results");
		loadResults.addActionListener(this);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("National election");
		comboBox.addItem("Local election");
		
		loaderPanel.add(loadResults);
		loaderPanel.add(comboBox);
		////////////////////////////////////////////////////////////////////////////////
		JPanel tablePanel = new JPanel();
		
		tablePanel.setBackground(Color.WHITE);
		
		Border tableBorder = BorderFactory.createTitledBorder("ELECTION RESULTS");
		tablePanel.setBorder(tableBorder);
		
		model = new DefaultTableModel();
		table = new JTable(model);
		
		table.getSelectionModel().addListSelectionListener(this);
		
		model.addColumn("Party");
		model.addColumn("Leader");
		model.addColumn("Seats");
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 400));
		
		tablePanel.add(scrollPane);
		////////////////////////////////////////////////////////////////////////////////
		JPanel statusPanel = new JPanel();
		
		statusPanel.setBackground(Color.WHITE);
		
		status = new JLabel("Load an election to get started!");
		
		statusPanel.add(status);
		////////////////////////////////////////////////////////////////////////////////
		JPanel combinedPanel = new JPanel(new BorderLayout());
		
		combinedPanel.add(tablePanel, BorderLayout.NORTH);
		combinedPanel.add(statusPanel, BorderLayout.SOUTH);
		////////////////////////////////////////////////////////////////////////////////
		JPanel utilitiesPanel = new JPanel();
		
		utilitiesPanel.setBackground(Color.LIGHT_GRAY);
		
		Border utilitiesBorder = BorderFactory.createTitledBorder("COALITION BUILDER");
		utilitiesPanel.setBorder(utilitiesBorder);
		
		addParty = new JButton("Add party");
		addParty.addActionListener(this);
		
		removeParty = new JButton("Remove party");
		removeParty.addActionListener(this);
		
		buildCoalition = new JButton("Build coalition");
		buildCoalition.addActionListener(this);
		
		utilitiesPanel.add(addParty);
		utilitiesPanel.add(removeParty);
		utilitiesPanel.add(buildCoalition);
		////////////////////////////////////////////////////////////////////////////////
		
		panel.add(loaderPanel, BorderLayout.NORTH);
		panel.add(combinedPanel, BorderLayout.CENTER);
		panel.add(utilitiesPanel, BorderLayout.SOUTH);
		
		frame.add(panel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == loadResults) {
			try {
				JFileChooser fileChooser = new JFileChooser("C:\\Users\\jjjcl\\Desktop\\Election Results for Project");
				Scanner file = null;
				
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = new Scanner(fileChooser.getSelectedFile());
					populateTable(file);
				}
				
				// Set type of election
				if (comboBox.getSelectedItem().equals("National election"))
					nationalElection = true;
				else
					nationalElection = false;
				
				status.setText("Loaded!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		if (arg0.getSource() == addParty) {
			int selectedRow = table.getSelectedRow();
			
			if (selectedRow != -1) {
				String name = (String) model.getValueAt(selectedRow, 0);
				String leader = (String) model.getValueAt(selectedRow, 1);
				int seats = (int) model.getValueAt(selectedRow, 2);
				
				if (nationalElection) {
					NationalParty party = new NationalParty(name, leader, seats);
					coalition.addParty(party);
				} else {
					LocalParty party = new LocalParty(name, leader, seats);
					coalition.addParty(party);
				}
				utilityGUI.setCoalitionDetails(coalition);
			} else {
				status.setText("No party is selected!");
			}
		}
		
		if (arg0.getSource() == removeParty) {
			int selectedRow = table.getSelectedRow();
			
			if (selectedRow != -1) {
				String name = (String) model.getValueAt(selectedRow, 0);
				String leader = (String) model.getValueAt(selectedRow, 1);
				int seats = (int) model.getValueAt(selectedRow, 2);
				
				if (nationalElection) {
					NationalParty party = new NationalParty(name, leader, seats);
					coalition.removeParty(party);
					
				} else {
					LocalParty party = new LocalParty(name, leader, seats);
					coalition.removeParty(party);
				}
			} else {
				status.setText("No party is selected!");
			}
		}
		
		if (arg0.getSource() == buildCoalition) {
			int totalSeats = 0;
			for (int i = 0; i < model.getRowCount(); i++) {
				totalSeats = totalSeats + (int) model.getValueAt(i, 2);
			}
			
			int coalitionSeats = 0;
			for (int i = 0; i < coalition.parties.size(); i++) {
				Party party = coalition.parties.get(i);
				coalitionSeats = coalitionSeats + party.getSeats();
			}
			
			utilityGUI.setCoalitionStatus(totalSeats, coalitionSeats);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		if (!arg0.getValueIsAdjusting()) {
			int selectedRow = table.getSelectedRow();
			
			if (selectedRow != -1) {
				String name = (String) model.getValueAt(selectedRow, 0);
				String leader = (String) model.getValueAt(selectedRow, 1);
				int seats = (int) model.getValueAt(selectedRow, 2);
				
				if (nationalElection) {
					NationalParty party = new NationalParty(name, leader, seats);
					utilityGUI.setPartyDetails(party);
				} else {
					LocalParty party = new LocalParty(name, leader, seats);
					utilityGUI.setPartyDetails(party);
				}
			}
		}
	}
	
	public void passGUI(UtilityGUI utilityGUI) {
		this.utilityGUI = utilityGUI;
	}
	
	private void populateTable(Scanner file) {
		model.setRowCount(0); // Clear the table
		coalition = new Coalition(); // Overwrite coalition
		
		file.nextLine(); // Skip heading
		
		while (file.hasNextLine()) {
			String line = file.nextLine();
			String[] contents = line.split(",");
			
			String name = contents[0];
			String leader = contents[1];
			int seats = Integer.parseInt(contents[2]);
			
			model.addRow(new Object[] { name, leader, seats });
		}
	}
}
