package ATCSimulation;

import java.util.Random;

/**
 * Simulates a real-life airplane flying in the sky, keeping track of the airplane's flight number, direct distance to runway,
 * elevation, and approach code
 */
public class Airplane {

	private String flightNum; //flight number
	private int directDist; //direct distance to runway in meters
	private int elevation; //elevation in meters
	private int AC; //approach code

	/**
	 * Creates an Airplane object with a given flight number
	 * @param flightNum flight number of airplane
	 */
	public Airplane(String flightNum) {
		this.flightNum = flightNum;
		directDist = generateDirectDist(); //set directDist to randomly generated direct distance value
		elevation = generateElevation(); //set elevation to randomly generated elevation value
		AC = calcAC (directDist, elevation); //calculate AC based on given formula
	}

	/**
	 * Creates an Airplane object with a given approach code
	 * @param AC approach code of Airplane
	 */
	public Airplane(int AC) {
		//note that the direct distance, elevation, and flight number values are not set for this Airplane object
		this.AC = AC;
	}

	/**
	 * Generates a random direct distance value of airplane to runway in the range between 3000 - 20000
	 * @return random direct distance value of airplane to runway
	 */
	private int generateDirectDist() {
		Random randomGen = new Random();
		//generates random integer from 3,000 - 20,000
		return randomGen.nextInt(17001) + 3000;
	}

	/**
	 * Generates a random elevation value between 1000 and 3000
	 * @return random elevation value between 1000 and 3000
	 */
	private int generateElevation(){
		Random randomGen = new Random();
		//generates random integer from 1,000 - 3,000
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
		//AC = 15,000 meters - (direct distance to runway in meter + Elevation in meter)/2
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
	 * Sets new flight number for airplane
	 * @param flightNum flight number that will replace airplane's current flight number
	 */
	public void setFlightNum (String flightNum){
		this.flightNum = flightNum;
	}
	/**
	 * Prints airplane's information in the format (flight number, direct distance, elevation) - AC: approach code
	 */
	public void printAirplaneInfo(){
		System.out.println("(" + flightNum +", D:" + directDist +
				" meters, H:" + elevation + " meters) - AC: "+ AC);
	}

}
