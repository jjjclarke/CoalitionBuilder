package com.coalitionbuilder;

public class LocalParty extends Party {

	public LocalParty(String name, String leader, int seats) {
		super(name, leader, seats);
	}

	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return "Party: " + getName() + " (local branch)\nLeader: " + getLeader() + "\nSeats: " + getSeats();
	}
}
