package com.coalitionbuilder;

public class NationalParty extends Party {
	public NationalParty(String name, String leader, int seats) {
		super(name, leader, seats);
	}

	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return "Party: " + getName() + " (national branch)\nLeader: " + getLeader() + "\nSeats: " + getSeats();
	}

}