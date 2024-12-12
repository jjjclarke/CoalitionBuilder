package com.coalitionbuilder;

public abstract class Party {
	private String name;
	private String leader;
	private int seats;

	public Party(String name, String leader, int seats) {
		super();
		this.name = name;
		this.leader = leader;
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public abstract String getDetails();

	@Override
	public String toString() {
		return "Name: " + getName() + ", Leader: " + getLeader() + ", Seats: " + getSeats() + ".";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Party other = (Party) obj;
		return name.equals(other.name) && leader.equals(other.leader) && seats == other.seats;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + leader.hashCode();
		result = 31 * result + seats;
		return result;
	}

}
