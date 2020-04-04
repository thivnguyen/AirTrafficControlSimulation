package ATCSimulator;

import java.util.Random;

public class Airplane {
	private int AC;
	private String flightNum;
	private int directDist;
	private int elevation;

	/**
	 * Creates an Airplane object with a given Flight Number
	 * @param flightNum flight number of airplane
	 */
	public Airplane(String flightNum) {
		this.flightNum = flightNum;
		directDist = generateDirectDist();
		elevation = generateElevation();
		AC = calcAC (directDist, elevation);
	}

	/**
	 * Creates an Airplane object with a given Flight Number
	 * @param AC approach code of Airplane
	 */
	public Airplane(int AC) {
		this.AC = AC;
	}

	public Airplane(String flightName, int AC) {
		this.AC = AC;
	}

	/**
	 * Generates a random direct distance value of airplane to runway in the range between 3000 - 20000
	 * @return random direct distance of airplane to runway
	 */
	private int generateDirectDist() {
		Random randomGen = new Random();
		//generates random integer from 3000 - 20,000
		return randomGen.nextInt(17001) + 3000;
	}

	/**
	 * Generates a random elevation value between 1000 and 3000
	 * @return random elevation value between 1000 and 3000
	 */
	private int generateElevation(){
		Random randomGen = new Random();
		//generates random integer from 1000 - 3000
		return randomGen.nextInt(2001) + 1000;
	}

	/**
	 * Returns flight number of airplane
	 * @return flight number of airplane
	 */
	public String getFlightNum() {
		return flightNum;
	}

	/**
	 * Returns direct distance of airplane
	 * @return direct distance of airplane
	 */
	public int getDirectDist() {
		return directDist;
	}

	/**
	 * Returns elevation of airplane
	 * @return elevation of airplane
	 */
	public int getElevation() {
		return elevation;
	}

	/**
	 * Returns approach code of airplane
	 * @return approach code of airplane
	 */
	public int getAC () {
		return AC;
	}

	/**
	 * Calculates approach code (AC) of airplane given the direct distance and elevation of airplane
	 * @param directDist direct distance of airplane
	 * @param elevation elevation of airplane
	 * @return approach code (AC) of airplane
	 */
	public int  calcAC (int directDist, int elevation) {
		return 15000 - (directDist + elevation)/2;
	}

	/**
	 * Sets new approach code value for airplane
	 * @param AC approach code that will replace airplane's current approach code
	 */
	public void setAC (int AC){
		this.AC = AC;
	}

	/**
	 * Prints the airplane's information in the format (Flight Number, direct distance, elevation) - AC: approach code
	 */
	public void printAirplaneInfo(){
		System.out.println("(" + flightNum +", D:" + directDist +
				" meters, H:" + elevation + " meters) - AC: "+ AC);
	}

//	public void setACRand(){
//		directDist = generateDirectDist();
//		elevation = generateElevation();
//		elevation = generateElevation();
//		AC = calcAC (directDist, elevation);
//	}
//
//
//	public void setDirectDist(int directDist){
//		this.directDist = directDist;
//	}
//	public void setElevation (int elevation){
//		this.elevation = elevation;
//	}

}
