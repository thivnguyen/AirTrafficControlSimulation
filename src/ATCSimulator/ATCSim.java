package ATCSimulator;

import java.util.ArrayList;
import java.util.Scanner;

public class ATCSim {

	private ArrayList<Airplane> airplanes;
	private ArrayList<Airplane> airplanesMaxHeap;

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
		airplanes.add (new Airplane("AA890"));
		airplanes.add (new Airplane("AA2892"));
		airplanes.add (new Airplane("AA6081"));
		airplanes.add (new Airplane("DL1845"));
		airplanes.add (new Airplane("DL1011"));
		airplanes.add (new Airplane("UA256"));
		airplanes.add (new Airplane("UA433"));
		airplanes.add (new Airplane("UA229"));
		airplanes.add (new Airplane("WN4949"));
		airplanes.add (new Airplane("WN3937"));
		airplanes.add (new Airplane("WN2549"));
		airplanes.add (new Airplane("F91044"));
		airplanes.add (new Airplane("B61705"));
		airplanes.add (new Airplane("NK550"));
		airplanes.add (new Airplane("B652"));
		airplanes.add (new Airplane("DL2323"));
		airplanes.add (new Airplane("WN2442"));
		airplanes.add (new Airplane("NK1130"));
		airplanes.add (new Airplane("NK910"));
		airplanes.add (new Airplane("F9679"));
		airplanes.add (new Airplane("AS35"));
		airplanes.add (new Airplane("AC1671"));
		airplanes.add (new Airplane("US1546)"));
		airplanes.add (new Airplane("B6252"));
		airplanes.add (new Airplane("AA2015"));
		airplanes.add (new Airplane("WN767"));
		airplanes.add (new Airplane("DL2518"));
		airplanes.add (new Airplane("NK515"));
		airplanes.add (new Airplane("B628"));
		airplanes.add (new Airplane("WN3317"));
		airplanesMaxHeap = buildMaxHeap (airplanes);
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

	public void userIncreaseAC(){
		ArrayList<Airplane> backward = listBackwards(heapSort(airplanes));
		printAirplaneList (backward);
		Scanner scan = new Scanner (System.in);
		System.out.println ("Please enter the position number of the plane that you want to increase the priority of: ");
		int index = scan.nextInt();
		Airplane toIncrease = backward.get(index);
		System.out.println ("Please enter a new AC value. Make sure the new AC is greater than current AC value (" +
				toIncrease.getAC() + ") and the AC of the plae before it in queue ("
				+ backward.get(getParent(index)).getAC() + "):");
		int newAC = scan.nextInt();

		int oldAC = backward.get(index).getAC();

		try{
			heapIncreaseKey(index,newAC);
		}
		catch (ATCSimException error){
			error.printStackTrace();
		}

		int newIndex = backward.indexOf(toIncrease);
		System.out.print ("Airplane at " + index + "with AC value of "+
				oldAC + "will be moved up to position" + newIndex +
				"after increasing the value to " + backward.get(newIndex).getAC());

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
		return (int)Math.ceil((index/2)) - 1;
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

		if (left < getHeapSize(planeList) - 1 && planeList.get(left).getAC() > planeList.get(i).getAC()) {
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
				newList.add (oldList.get (i)); //copy from oldList and insert into beginning of newList
			}
		}
		else if (newList.size() < end - start + 1){
			throw new ATCSimException("newList not long enough, will cause out of bounds error");
		}
		else{
			for (int i = start; i <= end; i++){
				newList.set (i, oldList.get (i)); //copy from oldList and insert into beginning of newList
			}
		}
		return newList;
	}

	public ArrayList<Airplane> heapSort(ArrayList<Airplane> planeList){
		ArrayList<Airplane> heap = buildMaxHeap (planeList);
		for (int i = heap.size()-1; i > 0; i--) {
			swap (heap, 0, i);
			ArrayList<Airplane> subList = new ArrayList<Airplane>(i);
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

	public void heapIncreaseKey(int index, int newAC) throws ATCSimException{
		Airplane plane = airplanesMaxHeap.get(index);
		if (newAC < plane.getAC()){
			throw new ATCSimException( "New AC value is too smaller tha current key!");
		}
		else if (newAC < getParent (index)){
			throw new ATCSimException( "New AC value is not large enough to move Airplane up in priority queue!");
		}
		airplanesMaxHeap.get(index).setAC (newAC);
		while (index > 0 && getParent(index) < plane.getAC()){
			swap (airplanesMaxHeap, index, getParent(index));
			index = getParent(index);
		}
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
			System.out.println("1 -> Add an Airplane to the list and heap:");
			System.out.println("2 -> Display Landing Sequence of Airplanes:");
			System.out.println("3 -> View Airplane first in queue to land: ");
			System.out.println("4 -> Increase an airplane priority in queue");

			int choice = 0;
			if (scan.hasNextInt()) {
				choice = Integer.parseInt(scan.nextLine());
			}

			switch (choice) {
				case 1:
					System.out.println ("Please enter a Flight Number: ");
					String flightNumber = scan.nextLine();
					Airplane plane = new Airplane (flightNumber);
					simulator.maxHeapInsert(plane);
					simulator.addAirplane(plane);
					break;

				case 2:
					System.out.println("Airplanes Landing sequence: ");
					simulator.printAirplaneListBackwards(simulator.heapSort(simulator.getAirplaneList()));
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

			//scan.nextLine();
			System.out.println("Is there another action you want to take? \n Enter \"YES\" or \"NO\"");
			String response = "";
			if (scan.hasNextLine()) {
				response = scan.nextLine();
			}
			response = response.toUpperCase();
			if (response .equals("NO")){
				done = true;
			}

		}
		scan.close();
	}

}
