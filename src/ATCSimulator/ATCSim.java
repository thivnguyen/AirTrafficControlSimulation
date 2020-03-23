package ATCSimulator;

import java.util.ArrayList;
import java.util.Scanner;

public class ATCSim extends ArrayList<Airplane>{

	public ATCSim() {
		//have list of 30 airplanes
		add (new Airplane("AA890"));
		add (new Airplane("AA2892"));
		add (new Airplane("AA6081"));
		add (new Airplane("DL1845"));
		add (new Airplane("DL1011"));
		add (new Airplane("UA256"));
		add (new Airplane("UA433"));
		add (new Airplane("UA229"));
		add (new Airplane("WN4949"));
		add (new Airplane("WN3937"));
		add (new Airplane("WN2549"));
		add (new Airplane("F91044"));
		add (new Airplane("B61705"));
		add (new Airplane("NK550"));
		add (new Airplane("B652"));
		add (new Airplane("DL2323"));
		add (new Airplane("WN2442"));
		add (new Airplane("NK1130"));
		add (new Airplane("NK910"));
		add (new Airplane("F9679"));
		add (new Airplane("AS35"));
		add (new Airplane("AC1671"));
		add (new Airplane("US1546)"));
		add (new Airplane("B6252"));
		add (new Airplane("AA2015"));
		add (new Airplane("WN767"));
		add (new Airplane("DL2518"));
		add (new Airplane("NK515"));
		add (new Airplane("B628"));
		add (new Airplane("WN3317"));
	}
	public static void main(String[] args) {
		
		ATCSim simulator = new ATCSim();
		
		System.out.println ("Please choose one of the following options by typing in the number from menu: ");
		System.out.println ("1 -> Add an Airplane:");
		System.out.println ("2 -> Display Sorted Result an Airplane:");
		
		Scanner scan = new Scanner (System.in);
		int choice = scan.nextInt();
		
		
		scan.close();		
	}
	
	public void userEnter() {
		Scanner scan = new Scanner (System.in);
		String flightNum = scan.nextLine();
		add(new Airplane(flightNum));
		scan.close();
	}
	
	public void displayResult (Airplane airplane) {
		int counter = 1; 
		for (Airplane a: this) {
			System.out.println(counter + ". (" + a.getFlightNum() +", D:" + a.getDirectDist() +
					" meters, H:" + a.getElevation() + " meters) - AC: "+ a.getAC());
			counter ++;
		}
	}
	
	public Airplane[] listToArray() {
		Airplane[] airplaneArr = new Airplane[this.size()];
		for (int i = 0; i < airplaneArr.length;i++) {
			airplaneArr[i] = this.get(0);
		}
		return airplaneArr;
	}
	
	private int getParent(int index) {
		return (index - 1)/2;
	}
	private int getLeft(int index) {
		return 2 * (index - 1);
	}
	
	private int getRight(int index) {
		//in book, it is 2 * index for array that starts @ 1
		//since arrays start @ 0 for Java, it is 2 * index - 1 + 1 = 2 * index
		return 2 * (index);
	}
	
	private int getHeapSize(Airplane[] arr)
	{
		return arr.length;
	}
	
	public void maxHeapify(Airplane[] arr, int i) {
		int left = getLeft(i);
		int right = getRight (i);
		int largest = 0;
		if (left < getHeapSize(arr) && arr[left].getAC() > arr[i].getAC()) {
			largest = left;
		}
		else {
			largest = i;
		}
		if (right < getHeapSize(arr) && arr[right].getAC() > arr[largest].getAC()) {
			largest = right;
		}
		if (largest != i) {
			//swap
			Airplane temp = arr [i];
			arr [i] = arr [largest];
			arr[largest] = temp;
			maxHeapify (arr, largest);
		}	
	}
	
}
