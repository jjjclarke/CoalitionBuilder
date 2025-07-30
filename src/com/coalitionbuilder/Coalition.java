package com.coalitionbuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Coalition {
	public ArrayList<Party> parties;
	
	public Coalition() {
		parties = new ArrayList<Party>();
	}
	
	public void addParty(Party party) {
		if (!parties.contains(party)) {
			parties.add(party);
			System.out.println("Added " + party.getName() + " to your coalition.");
		} else {
			System.out.println(party.getName() + " already exists in your coalition.");
		}
	}
	
	public void removeParty(Party party) {
		if (!parties.contains(party)) {
			System.out.println(party.getName() + " is not in your coalition.");
		} else {
			parties.remove(party);
			System.out.println(party.getName() + " has been removed from your coalition.");
		}
	}
	
	public String getDetails() {
	    StringBuilder details = new StringBuilder("Your Coalition:\n");
	    for (int i = 0; i < parties.size(); i++) {
	        Party party = parties.get(i);
	        details.append(" - ").append(party.getName()).append("\n")
	               .append("Has: ").append(party.getSeats()).append(" seats\n");
	    }
	    return details.toString();
	}
	
	public void saveCoalition() {
		try {
			PrintWriter out = new PrintWriter("coalition.txt");
			
			out.print(getDetails());
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
