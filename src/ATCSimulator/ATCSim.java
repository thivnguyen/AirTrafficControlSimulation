package ATCSimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ATCSim {

	private ArrayList<Airplane> airplanes;
	private ArrayList<Airplane> airplanesMaxHeap;
	private ArrayList<Airplane> airplanesQueue;

	public ATCSim (int numAirplane){
		airplanes = new ArrayList<Airplane>();
		airplanes.add (new Airplane("A",1));
		airplanes.add(new Airplane ("B", 10));
		airplanes.add(new Airplane ("C" , 13));
		airplanes.add(new Airplane ("D",15));
		airplanes.add(new Airplane ("E",2));
		airplanes.add(new Airplane ("F",26));
		airplanes.add(new Airplane ("G", 5));
		airplanes.add(new Airplane ("H",6));
		airplanes.add(new Airplane ("I",7));
		airplanes.add(new Airplane ("J",4));
		airplanes.add(new Airplane ("K",8));
		airplanes.add(new Airplane ("L",20));
		airplanes.add(new Airplane ("M",25));
		airplanes.add(new Airplane ("N",23));
		airplanes.add(new Airplane ("O",3));
	}

	public ATCSim() {
		//have list of 30 airplanes
		airplanes = new ArrayList<Airplane>();
		for (int i = 1; i<= 30; i++){
			airplanes.add (new Airplane (generateFlightNum()));
		}
		airplanesMaxHeap = buildMaxHeap (airplanes);
		airplanesQueue = makePriorityQueue (airplanesMaxHeap);
	}

	public String generateFlightNum(){
		Random rand = new Random();
		String flightNum = "";
		char firstLet = (char)(rand.nextInt(26) + 65);
		char secondLet = (char)(rand.nextInt(26) + 65);
		flightNum = ""+ firstLet + secondLet;
		int numDigits = rand.nextInt(3)+ 2;// random int 2 -> 3
		for (int i = 1; i<= numDigits; i++){
			int randomInt = rand.nextInt(9) + 1;
			flightNum = flightNum + randomInt;
		}
		return flightNum;
	}

	public ArrayList<Airplane> getAirplaneList(){
		return airplanes;
	}

	public void addAirplane(Airplane plane) {

		airplanes.add(plane);
		System.out.println("Airplane with the following information: ");
		printAirplaneInfo(plane);
		System.out.println("has been successfully added to the airplane list!");


	}

	public int findPlane (String flightNum, ArrayList<Airplane> plane){
		for (int i = 0; i < plane.size(); i++){
			Airplane a = plane.get(i);
			if (a.getFlightNum() .equals (flightNum)){
				return i;
			}
		}
		return -1;
	}

	public void userIncreaseAC(){
		ArrayList<Airplane> backward = listBackwards(heapSort(airplanes));
		printAirplaneList (backward);
		Scanner scan = new Scanner (System.in);
		System.out.println ("Please enter the position number of the plane that you want to increase the priority of: ");
		int number = scan.nextInt();
		int index = number - 1;
		Airplane toIncrease = backward.get(index);
		boolean ACHighEnough = false;
		int newAC = 0;
		while (!ACHighEnough){
		System.out.println ("Please enter a new AC value. Make sure the new AC is greater than current AC value (" +
				toIncrease.getAC() + ") and the AC of the plane before it in queue ("
				+ backward.get(index-1).getAC() + "):");
		newAC = scan.nextInt();
		if (newAC > toIncrease.getAC() && newAC > backward.get(index).getAC()){
				ACHighEnough = true;
			}
		}

		int indexInHeap = findPlane(backward.get(index).getFlightNum(), airplanesMaxHeap);
		int oldAC = backward.get(index).getAC();
		int newIndexHeap = 0;
		try{
			newIndexHeap = heapIncreaseKey(indexInHeap,newAC);
		}
		catch (ATCSimException error){
			error.printStackTrace();
		}

		int indexInQueue = findPlane (airplanesMaxHeap.get(newIndexHeap).getFlightNum(), backward); //since arraylist starts @ 0 and quque starts @ 1
		System.out.println ("Airplane at " + number + " with AC value of "+
				oldAC + " will be moved up to position " + indexInQueue +
				" in priority queue after increasing the value to " + newAC);

	}

	public ArrayList<Airplane> makePriorityQueue(ArrayList<Airplane> planeList){
		ArrayList<Airplane> queue = new ArrayList<Airplane>();
		for (int i = 0; i < planeList.size(); i++){
			try{
				queue.add(heapExtractMax());
			}
			catch (ATCSimException error){
				error.printStackTrace();
			}
		}
		return queue;
	}

	public void printAirplaneInfo(Airplane a){
		System.out.println("(" + a.getFlightNum() +", D:" + a.getDirectDist() +
				" meters, H:" + a.getElevation() + " meters) - AC: "+ a.getAC());
	}

	public void printAirplaneList (ArrayList<Airplane> planeList) {
		int counter = 1; 
		for (Airplane a: planeList) {
			System.out.print(counter + ".");
			printAirplaneInfo (a);
			counter++;
		}
	}

	public ArrayList<Airplane> listBackwards (ArrayList<Airplane> planeList){
		ArrayList<Airplane> backwards = new ArrayList <Airplane>();
		for (int i = planeList.size()-1; i >= 0; i--) {
			Airplane a = planeList.get (i);
			backwards.add(a);
		}
		return backwards;
	}

	public void printAirplaneListBackwards (ArrayList<Airplane> planeList) {
		int counter = 1;
		for (int i = planeList.size()-1; i>= 0; i--) {
			Airplane a = planeList.get (i);
			System.out.print(counter + ".");
			printAirplaneInfo (a);
			counter++;
		}
	}
	
	private int getParent(int index) {
		if (index == 0){
			return 0;
		}
		int difference = (int)Math.ceil((float)index/2);
		return difference - 1;
	}

	private int getLeft(int index) {
		return 2 * index + 1;
	}
	
	private int getRight(int index) {
		//in book, it is 2 * index for array that starts @ 1
		//since arrays start @ 0 for Java, it is 2 * index - 1 + 1 = 2 * index
		return 2 * index + 2;
	}
	
	private int getHeapSize(ArrayList<Airplane> planeList)
	{
		return planeList.size();
	}

	//swap elements at position in index1 and index2 of ArrayList
	private void swap (ArrayList<Airplane> planeList,int index1, int index2){
		Airplane temp = planeList.get(index1);
		planeList.set (index1,planeList.get(index2));
		planeList.set (index2, temp);
	}

	private ArrayList<Airplane> copyList (ArrayList<Airplane> original){
		ArrayList<Airplane> copy = new ArrayList();
		for (Airplane a: original){
			copy.add(a);
		}
		return copy;
	}

	public ArrayList<Airplane> maxHeapify(ArrayList<Airplane> airplaneList, int i) {

		ArrayList<Airplane> planeList = copyList (airplaneList);
		boolean done = false;

		int left = getLeft(i);
		int right = getRight (i);
		int largest = 0;

		if (left <= getHeapSize(planeList) - 1 && planeList.get(left).getAC() > planeList.get(i).getAC()) {
			largest = left;
		}
		else {
			largest = i;
		}
		if (right <= getHeapSize(planeList) -1 && planeList.get(right).getAC() > planeList.get(largest).getAC()) {
			largest = right;
		}
		if (largest != i) {
			//swap
			swap (planeList,i, largest);
			return maxHeapify (planeList, largest);
		}
		else {
			return planeList;
		}

	}

	public ArrayList<Airplane> buildMaxHeap (ArrayList<Airplane> planeList){
		ArrayList<Airplane> airplaneHeap = copyList(planeList);
		for (int i = getHeapSize(airplaneHeap) / 2 - 1; i >= 0 ;i--){
			airplaneHeap = maxHeapify (airplaneHeap, i);
		}
		return airplaneHeap;
	}

	/**
	 * Copies elements from start index to end index in oldList to newList
	 * @param oldList List where elements will be copied from
	 * @param newList List where elements will be copied to
	 * @param start first element inn oldList that will be copied
	 * @param end last element in oldList that will be copied
	 * @return newList with copied elements from oldList
	 */
	private ArrayList<Airplane> createSublist (ArrayList <Airplane> newList, ArrayList<Airplane> oldList, int start, int end) throws ATCSimException{
		if (newList.size() == 0){
			for (int i = start; i <= end; i++){
				newList.add (oldList.get (i)); //copy from oldList and insert into newList
			}
		}
		else if (newList.size() < end - start + 1){
			throw new ATCSimException("newList not long enough, will cause out of bounds error");
		}
		else{
			for (int i = start; i <= end; i++){
				newList.set (i, oldList.get (i)); //copy from oldList and replace values of newList
			}
		}
		return newList;
	}

	public ArrayList<Airplane> heapSort(ArrayList<Airplane> planeList){
		ArrayList<Airplane> heap = buildMaxHeap (planeList);
		for (int i = heap.size()-1; i > 0; i--) {
			swap (heap, 0, i);
			ArrayList<Airplane> subList = new ArrayList<Airplane>();
			try{
			createSublist (subList, heap, 0, i-1);
			subList = maxHeapify(subList, 0);
			createSublist (heap, subList, 0, i-1);}
			catch (ATCSimException error){
				error.printStackTrace();
			}
		}
		return heap;
	}

	public Airplane HeapMaximum (){
		return airplanesMaxHeap.get(0);
	}

	public Airplane heapExtractMax() throws ATCSimException{
		if (getHeapSize(airplanesMaxHeap) < 1){
			throw new ATCSimException("heap underflow");
		}
		Airplane maxPriority = airplanesMaxHeap.get(0);
		airplanesMaxHeap.set(0, airplanesMaxHeap.get(airplanesMaxHeap.size()-1));
		airplanesMaxHeap.remove (airplanesMaxHeap.size()-1);
		maxHeapify(airplanesMaxHeap, 0);
		return maxPriority;
	}

	public int heapIncreaseKey(int index, int newAC) throws ATCSimException{
		Airplane plane = airplanesMaxHeap.get(index);
		if (newAC < plane.getAC()){
			throw new ATCSimException( "New AC value is too smaller tha current key!");
		}
		else if (newAC < getParent (index)){
			throw new ATCSimException( "New AC value is not large enough to move Airplane up in priority queue!");
		}
		airplanesMaxHeap.get(index).setAC (newAC);
		while (index > 0 && airplanesMaxHeap.get(getParent(index)).getAC() < plane.getAC()){
			swap (airplanesMaxHeap, index, getParent(index));

				index = getParent(index);

		}
		return index;
	}

	//returns new index of airplane in heap
	public void maxHeapInsert (Airplane plane){
		Airplane lowestAC = new Airplane (-1);
		airplanesMaxHeap.add (lowestAC); //size will automatically increase
		lowestAC = plane;
		try {
			heapIncreaseKey(airplanesMaxHeap.size() - 1, plane.getAC());
		}
		catch (ATCSimException error){
			error.printStackTrace();
	}
		System.out.println("Airplane with the following information: ");
		printAirplaneInfo(plane);
		System.out.println("has been successfully added to the airplane heap!");
	}

	public static void main(String[] args) {

		ATCSim simulator = new ATCSim();
		Scanner scan = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			System.out.println("Please choose one of the following options by typing in the number from menu: ");
			System.out.println("1 -> Display Landing Sequence of Airplanes:");
			System.out.println("2 -> Add an Airplane to the list and heap: ");
			System.out.println("3 -> View Airplane first in queue to land: ");
			System.out.println("4 -> Increase an airplane priority in queue");
			System.out.println("5 -> Land the first airplane in queue");

			int choice = 0;
			if (scan.hasNextInt()) {
				choice = Integer.parseInt(scan.nextLine());
			}

			switch (choice) {
				case 1:
					System.out.println("Airplanes Landing sequence: ");
					simulator.printAirplaneListBackwards(simulator.heapSort(simulator.getAirplaneList()));
					break;

				case 2:
					System.out.println ("Please enter a Flight Number: ");
					String flightNumber = scan.nextLine();
					Airplane plane = new Airplane (flightNumber);
					simulator.maxHeapInsert(plane);
					simulator.addAirplane(plane);
					break;

				case 3:
					System.out.println("First plane in queue to land: ");
					try{
					simulator.printAirplaneInfo(simulator.heapExtractMax());}
					catch (ATCSimException error){
						error.printStackTrace();
					}
					break;

				case 4:
					simulator.userIncreaseAC();
			}

			boolean correctResponse = false;
			System.out.println("Is there another action you want to take? \n Enter \"YES\" or \"NO\"");
			while (!correctResponse){
				String response = "";
				if (scan.hasNextLine()) {
					response = scan.nextLine();
				}
				response = response.toUpperCase();
				if (response .equals("NO")){
					correctResponse = true;
					done = true;
					System.out.println ("Thank you. Simulation ends. Have a great day!");
				}
				else if (response .equals("YES")){
					correctResponse = true;
				}
				else{
					System.out.println ("Incorrect Input. Please make sure you are in teh correct format. Please enter \"YES\" or \"NO\"");
				}
			}


		}
		scan.close();
	}

}
