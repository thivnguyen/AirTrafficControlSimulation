package ATCSimulator;

import java.util.ArrayList;
import java.util.Scanner;

public class ATCSim {

	private ArrayList<Airplane> airplanes;
	private ArrayList<Airplane> airplanesHeap;

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
		airplanesHeap = buildMaxHeap (airplanes);
	}

	public ArrayList<Airplane> getAirplaneList(){
		return airplanes;
	}

	public void userEnter() {
		Scanner scan = new Scanner (System.in);
		String flightNum = scan.nextLine();
		airplanes.add(new Airplane(flightNum));
		scan.close();
	}
	
	public void printAirplaneList (ArrayList<Airplane> planeList) {
		int counter = 1; 
		for (Airplane a: planeList) {
			System.out.println(counter + ". (" + a.getFlightNum() +", D:" + a.getDirectDist() +
					" meters, H:" + a.getElevation() + " meters) - AC: "+ a.getAC());
			counter ++;
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

	public ArrayList<Airplane> heapSort(ArrayList<Airplane> planeList){
		ArrayList<Airplane> heap = buildMaxHeap (planeList);
		ArrayList<Airplane> sorted = new ArrayList<>();
		for (int i = heap.size()-1; i >= 0; i--) {
			swap (heap, 0, i);
			sorted.add(heap.get(i));
			heap.remove (i);
			heap = maxHeapify(heap, 0);
		}
		return sorted;
	}

	public static void main(String[] args) {

		ATCSim simulator = new ATCSim();

		System.out.println ("Please choose one of the following options by typing in the number from menu: ");
		System.out.println ("1 -> Add an Airplane:");
		System.out.println ("2 -> Display Sorted Result an Airplane:");

		Scanner scan = new Scanner (System.in);
		int choice = scan.nextInt();

		switch (choice){
			case 1:
				simulator.userEnter();

			case 2:
				simulator.printAirplaneList(simulator.heapSort(simulator.getAirplaneList()));
		}

		scan.close();
	}
}
