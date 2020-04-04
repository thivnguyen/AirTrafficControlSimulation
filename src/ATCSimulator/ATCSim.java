package ATCSimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class ATCSim {

    private ArrayList<Airplane> airplanesList;
    private ArrayList<Airplane> airplanesQueue;

    public ATCSim(int numAirplane) {
        airplanesList = new ArrayList<Airplane>();
        airplanesList.add(new Airplane("A", 1));
        airplanesList.add(new Airplane("B", 10));
        airplanesList.add(new Airplane("C", 13));
        airplanesList.add(new Airplane("D", 15));
        airplanesList.add(new Airplane("E", 2));
        airplanesList.add(new Airplane("F", 26));
        airplanesList.add(new Airplane("G", 5));
        airplanesList.add(new Airplane("H", 6));
        airplanesList.add(new Airplane("I", 7));
        airplanesList.add(new Airplane("J", 4));
        airplanesList.add(new Airplane("K", 8));
        airplanesList.add(new Airplane("L", 20));
        airplanesList.add(new Airplane("M", 25));
        airplanesList.add(new Airplane("N", 23));
        airplanesList.add(new Airplane("O", 3));
    }

    /**
     * Constructs 30 Airplane Objects and stores into an airplane list and an airplane priority queue
     */
    public ATCSim() {
        //Creates 30 Airplane objects
        airplanesList = new ArrayList<Airplane>();
        for (int i = 1; i <= 30; i++) {
            airplanesList.add(new Airplane(generateFlightNum())); //generate FlightName randomly
        }
        ArrayList<Airplane> airplanesMaxHeap = buildMaxHeap(airplanesList); //build a max heap
        airplanesQueue = makePriorityQueue(airplanesMaxHeap); //build priority queue
    }

    /**
     * Generate a random Flight Number that consists of two alphabet letters and 2-4 digits
     *
     * @return randomly generated Flight Number
     */
    private String generateFlightNum() {
        Random rand = new Random();
        String flightNum = "";
        char firstLet = (char) (rand.nextInt(26) + 65); //Generate random character A-Z
        char secondLet = (char) (rand.nextInt(26) + 65); //Generate random character A-Z
        flightNum = "" + firstLet + secondLet; //Concatenate those characters

        //generate numbers in Flight Number
        // generate random int 2 -> 4, since flight numbers usually have 2 - 4 digits
        int numDigits = rand.nextInt(3) + 2;
        for (int i = 1; i <= numDigits; i++) {
            int randomInt = rand.nextInt(9) + 1; //random digit 1 - 9
            flightNum = flightNum + randomInt; //concatenate number to flightnum
        }
        return flightNum;
    }

    /**
     * Returns airplane list in order that Airplanes were added
     *
     * @return airplane list in order that Airplanes were added
     */
    public ArrayList<Airplane> getAirplaneList() {
        return airplanesList;
    }

    /**
     * Returns airplane priority queue
     *
     * @return airplane priority queue
     */
    public ArrayList<Airplane> getAirplaneQueue() {
        return airplanesQueue;
    }

    /**
     * Returns index of the parent node
     *
     * @param index index that you want to get the parent of
     * @return index of parent node
     */
    private int getParent(int index) {
        if (index == 0) { //if the index is already the root, it has no parent, so return it's own index
            return 0;
        }
        return (int) Math.ceil((float) index / 2) - 1;
    }

    /**
     * Returns index of the left node
     *
     * @param index index that you want to get the left node of
     * @return index of the left node
     */
    private int getLeft(int index) {
        return 2 * index + 1;
    }

    /**
     * Returns index of the right node
     *
     * @param index index that you want to get the right node of
     * @return index of the right node
     */
    private int getRight(int index) {
        return 2 * index + 2;
    }

    /**
     * Return the size of the heap
     *
     * @param planeList heap
     * @return size of heap
     */
    private int getHeapSize(ArrayList<Airplane> planeList) {
        return planeList.size();
    }

    /**
     * Adds airplane to airplane list
     *
     * @param plane Airplane to be added
     */
    public void addAirplane(Airplane plane) {

        airplanesList.add(plane);

        //Tells users that the Airplane has been successfully added
        System.out.println("Airplane with the following information: ");
        plane.printAirplaneInfo(); //prints the airplane's info
        System.out.println("has been successfully added to the airplane list!");
    }

    /**
     * Remove airplane from airplane list
     *
     * @param i index of Airplane that should be removed
     */
    public void removeFromAirplaneList(int i) {
        airplanesList.remove(i);
    }

    /**
     * Search for plane in list that matches the flightNum
     *
     * @param flightNum flight number of airplane
     * @param planeList list of planes where plane will be searched for
     * @return index of plane, -1 if plane was not found
     */
    public int findPlane(String flightNum, ArrayList<Airplane> planeList) {

        for (int i = 0; i < planeList.size(); i++) {
            Airplane a = planeList.get(i);
            if (a.getFlightNum().equals(flightNum)) { //if plane FlightNum matches, return the index of that plane
                return i;
            }
        }
        return -1;
    }

    /**
     * Allows user to choose airplane and increase its priority
     */
    public void userIncreasePriority() {

        printAirplaneList(airplanesQueue); //Prints Queue so User can see all airplanes currently in line to land

        ///Allow users to choose airplane that they want to increase priority of
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the position number of the plane that you want to increase the priority of: ");
        int numberInQueue = scan.nextInt();
        int index = numberInQueue - 1; //Priority queue displays value from 1, subtract 1 to get index of Airplane in ArrayList

        //Retrieve Airplane from Priority Queue
        Airplane toIncrease = airplanesQueue.get(index);
        String flightNum = toIncrease.getFlightNum(); //store name so can search for plane later on

        //Allow users to choose new AC value
        //Make sure the value is high enough to move plane up the queue
        boolean ACHighEnough = false;
        int newAC = 0;
        while (!ACHighEnough) {
            System.out.println("Please enter a new AC value. Make sure the new AC is greater than current AC value (" +
                    toIncrease.getAC() + ") and the AC of its parent in the queue ("
                    + airplanesQueue.get(getParent(index)).getAC() + "):");
            newAC = scan.nextInt();
            if (newAC > toIncrease.getAC() && newAC > airplanesQueue.get(index).getAC()) {
                ACHighEnough = true;
            }
        }

        int oldAC = airplanesQueue.get(index).getAC(); //keep track of original AC, so it can be printed later

        //increase the key of the Airplane
        try {
            heapIncreaseKey(index, newAC);
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        //Since the airplane might have moved up in the heap, but still not in the right order for the priority heap,
        //then call makePriorityQueue to ensure that the nodes are in the order of a priority queue
        airplanesQueue = makePriorityQueue(airplanesQueue);
        int newIndex = findPlane(flightNum, airplanesQueue); //search for plane and store Airplane's new index in heap
        System.out.println("Airplane at " + numberInQueue + " with AC value of " +
                oldAC + " will be moved up to position " + (newIndex + 1) +
                " in priority queue after increasing the value to " + airplanesQueue.get(newIndex).getAC());
    }

    /**
     * Create a Priority Queue given a maxheap
     *
     * @param maxHeap maxHeap that Priority Queue will be created from
     * @return priority queue
     */
    public ArrayList<Airplane> makePriorityQueue(ArrayList<Airplane> maxHeap) {
        ArrayList<Airplane> queue = new ArrayList<Airplane>();
        int size = maxHeap.size();

        //Keep extracting the first Node of the maxHeap util there is none left
        for (int i = 0; i < size; i++) {
            try {
                queue.add(heapExtractMax(maxHeap));
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        return queue;
    }

    /**
     * Print the Airplane List
     *
     * @param planeList Airplane List to be printed
     */
    public void printAirplaneList(ArrayList<Airplane> planeList) {
        int counter = 1; // counter will indicate position of Airplane in Queue when printed
        for (Airplane a : planeList) {
            System.out.print(counter + ".");
            a.printAirplaneInfo();
            counter++;
        }
    }

    /**
     * Print the list backwards, from the last element to the first element
     *
     * @param planeList list to be printed
     */
    public void printAirplaneListBackwards(ArrayList<Airplane> planeList) {
        int counter = 1; // counter will indicate position of Airplane in Queue when printed
        for (int i = planeList.size() - 1; i >= 0; i--) {
            Airplane a = planeList.get(i);
            System.out.print(counter + ".");
            a.printAirplaneInfo();
            counter++;
        }
    }

    /**
     * Swap elements at position in index1 and index2 of ArrayList
     *
     * @param planeList list where elements are located
     * @param index1    first element to be swapped
     * @param index2    second element to be swapped
     */
    private void swap(ArrayList<Airplane> planeList, int index1, int index2) {
        Airplane temp = planeList.get(index1);
        planeList.set(index1, planeList.get(index2));
        planeList.set(index2, temp);
    }

    /**
     * Make copy of list
     *
     * @param original list to be copied
     * @return copied list of original list
     */
    private ArrayList<Airplane> copyList(ArrayList<Airplane> original) {
        ArrayList<Airplane> copy = new ArrayList();
        for (Airplane a : original) {
            copy.add(a);
        }
        return copy;
    }

    /**
     * Move node @ index i to correct position in Heap so that the max-heap property can be maintained
     *
     * @param planeList Heap where node currently is at
     * @param i         index of node that will be moved to right position
     */
    public void maxHeapify(ArrayList<Airplane> planeList, int i) {

        int left = getLeft(i); //index of node to the left
        int right = getRight(i); //index of node to the right
        int largest = 0;

        if (left <= getHeapSize(planeList) - 1 && planeList.get(left).getAC() > planeList.get(i).getAC()) {
            largest = left;
        } else {
            largest = i;
        }
        if (right <= getHeapSize(planeList) - 1 && planeList.get(right).getAC() > planeList.get(largest).getAC()) {
            largest = right;
        }
        if (largest != i) {
            //swap node i with the node that has the largest value in order to move the node with a larger value up
            // and node i down
            swap(planeList, i, largest);

            //call maxheapify on the node that has the largest value between the node
            //the node's left node, and the node's right node
            maxHeapify(planeList, largest);

        }

    }

    /**
     * Creates a max heap from a given list
     *
     * @param planeList Airplane list that max heap will be created from
     * @return maxheap that was created
     */
    public ArrayList<Airplane> buildMaxHeap(ArrayList<Airplane> planeList) {

        ArrayList<Airplane> airplaneHeap = copyList(planeList);

        //call Max-heapify on each element in a bottom-up manner
        for (int i = getHeapSize(airplaneHeap) / 2 - 1; i >= 0; i--) {
            maxHeapify(airplaneHeap, i); //maxHeapify the node so that it will go to the right place in the heap
        }
        return airplaneHeap;
    }

    /**
     * Sorts Airplanes from lowest to greatest AC value
     *
     * @param planeList Airplane list to be sorted
     * @return sorted list of Airplanes from lowest to highest AC value
     */
    public ArrayList<Airplane> heapSort(ArrayList<Airplane> planeList) {
        ArrayList<Airplane> heap = buildMaxHeap(planeList); //create a maxHeap from the given planeList
        for (int i = heap.size() - 1; i > 0; i--) {
            //move maximum element to the it's correct position by swapping
            swap(heap, 0, i);

            //Restore heap property without touching the elements that are already in teh correct position
            ArrayList<Airplane> subList = new ArrayList<Airplane>();
            try {
                //copy elements from 0 to i-1 to another list
                createSublist(subList, heap, 0, i - 1);
                //use maxheapify to restore the max-heap property
                maxHeapify(subList, 0);
                //add the elements back to the original list
                createSublist(heap, subList, 0, i - 1);
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        return heap;
    }

    /**
     * Returns Airplane with greatest AC value
     *
     * @return Airplane with greatest AC value
     */
    public Airplane heapMaximum() {
        //since first element in queue has the highest AC value, return first element
        return airplanesQueue.get(0);
    }

    /**
     * Removes and returns the Airplane with the greatest AC value
     *
     * @param planeList maxheap where airplane wil be extracted
     * @return Airplane that was extracted
     * @throws ATCSimException if list is empty
     */
    public Airplane heapExtractMax(ArrayList<Airplane> planeList) throws ATCSimException {

        //checks if list is empty
        if (getHeapSize(planeList) < 1) {
            throw new ATCSimException("heap underflow");
        }

        Airplane maxPriority = planeList.get(0);
        planeList.set(0, planeList.get(planeList.size() - 1)); //move last element to the front of the list
        planeList.remove(planeList.size() - 1);
        maxHeapify(planeList, 0); //restore max-heap property
        return maxPriority; //return Airplane that has greatest AC value
    }

    /**
     * Increase AC value of Airplane and moves it to the correct position in heap
     *
     * @param index current index of Airplane in heap
     * @param newAC new AC value for the airplane
     * @throws ATCSimException if new AC value is not large enough to be moved up in max-heap
     */
    public void heapIncreaseKey(int index, int newAC) throws ATCSimException {
        Airplane plane = airplanesQueue.get(index);

        if (newAC < plane.getAC()) { //if key is less than the current key
            throw new ATCSimException("New AC value is too smaller than current key!");
        }

        //set new AC value for Airplane
        airplanesQueue.get(index).setAC(newAC);

        //swap as long as the key is greater than the parent's key
        while (index > 0 && airplanesQueue.get(getParent(index)).getAC() < plane.getAC()) {
            swap(airplanesQueue, index, getParent(index));
            index = getParent(index);
        }
    }

    /**
     * Inserts new Airplane into the Priority Queue
     *
     * @param plane airplane to be inserted
     */
    public void maxHeapInsert(Airplane plane) {
        //inserts an airplane object with -1 value (value lower than all other AC values)
        Airplane lowestAC = new Airplane(-1);
        airplanesQueue.add(lowestAC); //size will automatically increase
        lowestAC = plane; //sets the plane with -1 AC value to plane that needs to be inserted
        try {
            //call heapIncrease key with the plane's AC value so it can be placed at the correct position
            heapIncreaseKey(airplanesQueue.size() - 1, plane.getAC());
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        //Tells user that the plane has been successfully added to the priority queue
        System.out.println("Airplane with the following information: ");
        plane.printAirplaneInfo();
        System.out.println("has been successfully added to the airplane heap!");
    }

    /**
     * Inserts airplane into priority queue
     * @param plane airplane to be inserted
     */
    public void insertQueue (Airplane plane){
        maxHeapInsert(plane);
    }

    /**
     * Copies elements from start index to end index in oldList to newList
     *
     * @param oldList List where elements will be copied from
     * @param newList List where elements will be copied to
     * @param start   first element in oldList that will be copied
     * @param end     last element in oldList that will be copied
     * @return newList with copied elements from oldList
     */
    private ArrayList<Airplane> createSublist(ArrayList<Airplane> newList, ArrayList<Airplane> oldList, int start, int end) throws ATCSimException {
        if (newList.size() == 0) {
            for (int i = start; i <= end; i++) {
                newList.add(oldList.get(i)); //copy from oldList and insert into newList
            }
        } else if (newList.size() < end - start + 1) {
            throw new ATCSimException("newList not long enough, will cause out of bounds error");
        } else {
            for (int i = start; i <= end; i++) {
                newList.set(i, oldList.get(i)); //copy from oldList and replace values of newList
            }
        }
        return newList;
    }

    public static void main(String[] args) {

        ATCSim simulator = new ATCSim();
        Scanner scan = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Please choose one of the following options by typing in the number from menu: ");
            System.out.println("1 -> Display Landing Sequence of Airplanes (sorted using Heapsort)");
            System.out.println("2 -> Add an Airplane to the list and queue ");
            System.out.println("3 -> View first airplane in queue ");
            System.out.println("4 -> Increase an airplane priority in queue");
			System.out.println("5 -> Land the first Airplane in queue ");
            System.out.println("6 -> Land the first airplane in queue");


            boolean legitChoice = false;
            while (!legitChoice) {
                int choice = 0;
                if (scan.hasNextInt()) {
                    choice = Integer.parseInt(scan.nextLine());
                }

                switch (choice) {
                    case 1:
                    	legitChoice = true;
                        System.out.println("Airplanes Landing sequence (sorted using Heapsort): ");
                        simulator.printAirplaneListBackwards(simulator.heapSort(simulator.getAirplaneList()));
                        break;
                    case 2:
						legitChoice = true;
                        System.out.println("Please enter a Flight Number: ");
                        String flightNumber = scan.nextLine();
                        Airplane plane = new Airplane(flightNumber);
                        simulator.maxHeapInsert(plane);
                        simulator.addAirplane(plane);
                        break;

                    case 3:
                        legitChoice = true;
                        System.out.println("First airplane in queue: ");
                        Airplane first = simulator.heapMaximum();
                        first.printAirplaneInfo();
                        break;

                    case 4:
						legitChoice = true;
                        simulator.userIncreasePriority();
                        System.out.println("Updated Airplane Queue: ");
                        simulator.printAirplaneList(simulator.getAirplaneQueue());
                        break;

                    case 5:
						legitChoice = true;
                        try {
                            Airplane extracted = simulator.heapExtractMax(simulator.getAirplaneQueue());
                            int index = simulator.findPlane(extracted.getFlightNum(), simulator.getAirplaneList());
                            simulator.removeFromAirplaneList(index);
                            System.out.println("The following airplane has landed: ");
                            extracted.printAirplaneInfo();
                        } catch (ATCSimException error) {
                            error.printStackTrace();
                        }
                        break;

                    default:
                        System.out.println("Option does not exist, please enter another one");

                }
            }
            boolean correctResponse = false;
            System.out.println("Is there another action you want to take? \n Enter \"YES\" or \"NO\"");
            while (!correctResponse) {
                String response = "";
                if (scan.hasNextLine()) {
                    response = scan.nextLine();
                }
                response = response.toUpperCase();
                if (response.equals("NO")) {
                    correctResponse = true;
                    done = true;
                    System.out.println("Thank you. Simulation ends. Have a great day!");
                } else if (response.equals("YES")) {
                    correctResponse = true;
                } else {
                    System.out.println("Incorrect Input. Please make sure you are in teh correct format. Please enter \"YES\" or \"NO\"");
                }
            }
        }
        scan.close();
    }

}
