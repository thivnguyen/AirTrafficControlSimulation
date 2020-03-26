package ATCSimulator;

import java.util.Random;

public class Airplane {
	private int AC;
	private String flightNum;
	private int directDist;
	private int elevation;
	
	public Airplane(String flightNum) {
		this.flightNum = flightNum;
		directDist = generateDirectDist();
		elevation = generateElevation();
		AC = calcAC (directDist, elevation);
	}

	public Airplane(String flightNum, int AC) {
		this.flightNum = flightNum;
		this.AC = AC;
	}

	private int generateDirectDist() {
		Random randomGen = new Random();
		//generates random integer from 3000 - 20,000
		return randomGen.nextInt(17001) + 3000;
	}
	
	private int generateElevation(){
		Random randomGen = new Random();
		//generates random integer from 1000 - 3000
		return randomGen.nextInt(2001) + 1000;
	}
	
	public String getFlightNum() {
		return flightNum;
	}
	
	public int getDirectDist() {
		return directDist;
	}
	
	public int getElevation() {
		return elevation;
	}
	
	public int getAC () {
		return AC;
	}
	
	public int  calcAC (int directDist, int elevation) {
		return 15000 - (directDist + elevation)/2;
	}
	
}
