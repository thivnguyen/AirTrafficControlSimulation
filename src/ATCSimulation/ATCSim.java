package ATCSimulation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Simulates a real Air Traffic Control Tower that receives and stores airplane information, as well as use the
 * airplane’s direct distance to runway and elevation to decide the order the airplanes will land
 */
public class ATCSim {

    private ArrayList<Airplane> airplanesList; //stores airplanes in order that they were added
    private ArrayList<Airplane> airplanesQueue; //stores airplanes in order that they will land

    /**
     * Constructs 30 Airplane objects and stores into an airplane list and an airplane priority queue
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
     * Generate a random flight number that consists of two alphabet letters and 2-4 digits
     *
     * @return randomly generated flight number
     */
    private String generateFlightNum() {
        Random rand = new Random();
        String flightNum = "";
        char firstLet = (char) (rand.nextInt(26) + 65); //generate random character A-Z
        char secondLet = (char) (rand.nextInt(26) + 65); //generate random character A-Z
        flightNum = "" + firstLet + secondLet; //concatenate those characters

        //generate numbers in flight number
        // generate random int 2 -> 4, since flight numbers usually have 2 - 4 digits
        int numDigits = rand.nextInt(3) + 2;
        for (int i = 1; i <= numDigits; i++) {
            int randomInt = rand.nextInt(9) + 1; //random digit 1 - 9
            flightNum = flightNum + randomInt; //concatenate number to flightnum
        }
        return flightNum;
    }

    /**
     * Returns airplane list in order that airplanes were added
     *
     * @return airplane list in order that airplanes were added
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
     * Returns index of parent node
     *
     * @param index index that user want to get parent of
     * @return index of parent node
     */
    private int getParent(int index) {
        if (index == 0) { //if the index is already the root, it has no parent, so return its own index
            return 0;
        }
        return (int) Math.ceil((float) index / 2) - 1;
    }

    /**
     * Returns index of left node
     *
     * @param index index that user want to get left node of
     * @return index of left node
     */
    private int getLeft(int index) {
        return 2 * index + 1;
    }

    /**
     * Returns index of right node
     *
     * @param index index that user want to get the right node of
     * @return index of the right node
     */
    private int getRight(int index) {
        return 2 * index + 2;
    }

    /**
     * Return size of heap
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
     * @param plane airplane to be added
     */
    public void addAirplane(Airplane plane) {

        airplanesList.add(plane);

        //Tells users that Airplane has been successfully added
        System.out.println("Airplane with the following information: ");
        plane.printAirplaneInfo(); //prints airplane's info
        System.out.println("has been successfully added to the airplane list!");
    }

    /**
     * Remove airplane from airplane list
     *
     * @param i index of airplane that should be removed
     */
    public void removeFromAirplaneList(int i) {
        airplanesList.remove(i);
    }

    /**
     * Search for plane in list that matches given flight number
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

        //prints airplane queue so user can see all airplanes currently in line to land
        printAirplaneList(airplanesQueue);

        //allow users to choose airplane that they want to increase priority of
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the position number of the plane that you want to increase the priority of: ");
        int numberInQueue = scan.nextInt();
        int index = numberInQueue - 1; //priority queue displays value from 1, subtract 1 to get index of Airplane in ArrayList

        //retrieve Airplane from priority queue
        Airplane toIncrease = airplanesQueue.get(index);
        String flightNum = toIncrease.getFlightNum(); //store name so can search for plane later on

        //allow users to choose new AC value
        //make sure the value is high enough to move plane up the queue
        boolean ACHighEnough = false;
        int newAC = 0;
        while (!ACHighEnough) {
            System.out.println("Please enter a new AC value. Make sure the new AC is greater than current AC value (" +
                    toIncrease.getAC() + ") and the AC of the airplane before it in the queue ("
                    + airplanesQueue.get(index-1).getAC() + "):");
            try{
                newAC = scan.nextInt();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            //makes sure the new AC value is greater than the current AC value and greater than AC value of plane
            //before it in queue
            if (newAC > toIncrease.getAC() && newAC > airplanesQueue.get(index-1).getAC()) {
                ACHighEnough = true;
            }
        }

        int oldAC = airplanesQueue.get(index).getAC(); //keep track of original AC, so it can be printed later

        //increase the key of the Airplane and move it to right location in max heap
        toIncrease.setAC(newAC);
        try {
            heapIncreaseKey(index, toIncrease);
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        //since airplane might have moved up in the heap, but still not in the right order for the priority queue,
        //call makePriorityQueue to ensure that the nodes are in the order of a priority queue (highest to lowest AC value)
        airplanesQueue = makePriorityQueue(airplanesQueue);
        int newIndex = findPlane(flightNum, airplanesQueue); //search for plane and store Airplane's new index in heap
        System.out.println("Airplane at " + numberInQueue + " with AC value of " +
                oldAC + " will be moved up to position " + (newIndex + 1) +
                " in priority queue after increasing the value to " + airplanesQueue.get(newIndex).getAC());
    }

    /**
     * Create priority queue given a max heap
     *
     * @param maxHeap maxHeap that priority queue will be created from
     * @return priority queue
     */
    public ArrayList<Airplane> makePriorityQueue(ArrayList<Airplane> maxHeap) {
        ArrayList<Airplane> queue = new ArrayList<Airplane>();
        int size = maxHeap.size();

        //Keep extracting the first node of the max heap until there is none left
        for (int i = 0; i < size; i++) {
            try {
                //add extracted node to priority queue
                queue.add(heapExtractMax(maxHeap));
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        return queue;
    }

    /**
     * Insert airplane into priority queue
     * @param plane airplane to be inserted
     */
    public void insertQueue (Airplane plane){

        //insert plane into max heap
        maxHeapInsert(plane);

        //maxHeapInsert inserts plane into the right location in the max heap, not the priority queue.
        //Therefore, the airplanes are not ordered from greatest to smallest AC after maxHeapInsert is called.
        //makePriorityQueue is called again to make sure the queue is in order from highest to lowest AC
        airplanesQueue = makePriorityQueue(airplanesQueue);
    }

    /**
     * Print airplane list
     *
     * @param planeList airplane list to be printed
     */
    public void printAirplaneList(ArrayList<Airplane> planeList) {
        int counter = 1; // counter will indicate position of Airplane in queue when printed
        for (Airplane a : planeList) {
            System.out.print(counter + ". ");
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
        int counter = 1; // counter will indicate position of Airplane in queue when printed
        for (int i = planeList.size() - 1; i >= 0; i--) {
            Airplane a = planeList.get(i);
            System.out.print(counter + ". ");
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
     * Move node @ index i to correct position in heap so that max-heap property can be maintained
     *
     * @param planeList heap where node currently part of
     * @param i         index of node that will be moved to right position
     */
    public void maxHeapify(ArrayList<Airplane> planeList, int i) {

        int left = getLeft(i); //index of node to the left
        int right = getRight(i); //index of node to the right
        int largest = 0;

        //if left node index exist in heap and AC value of left > AC value of i
        if (left <= getHeapSize(planeList) - 1 && planeList.get(left).getAC() > planeList.get(i).getAC()) {
            largest = left;
        } else {
            largest = i;
        }

        ///if right node index exist in heap and AC value of right > AC value of largest
        if (right <= getHeapSize(planeList) - 1 && planeList.get(right).getAC() > planeList.get(largest).getAC()) {
            largest = right;
        }

        //if index of largest != index of i
        if (largest != i) {
            //swap node i with the node that has the largest value in order to move the node with a larger value up
            // and node i down
            swap(planeList, i, largest);

            //call maxheapify on the node that has the largest value between node
            //node's left node, and node's right node
            maxHeapify(planeList, largest);

        }

    }

    /**
     * Create max heap from given list
     *
     * @param planeList airplane list that max heap will be created from
     * @return maxheap that was created
     */
    public ArrayList<Airplane> buildMaxHeap(ArrayList<Airplane> planeList) {

        ArrayList<Airplane> airplaneHeap = copyList(planeList);

        //call maxHeapify on each element in a bottom-up manner
        for (int i = getHeapSize(airplaneHeap) / 2 - 1; i >= 0; i--) {
            maxHeapify(airplaneHeap, i); //call maxHeapify on node so that it will go to the right place in the heap
        }
        return airplaneHeap;
    }

    /**
     * Sort airplanes from lowest to greatest AC value
     *
     * @param planeList airplane list to be sorted
     * @return sorted list of airplanes from lowest to highest AC value
     */
    public ArrayList<Airplane> heapsort(ArrayList<Airplane> planeList) {
        ArrayList<Airplane> heap = buildMaxHeap(planeList); //create a max heap from the given plane list
        for (int i = heap.size() - 1; i > 0; i--) {
            //move maximum element to the it's correct position by swapping
            swap(heap, 0, i);

            //simulate that heap size has been decreased by 1 by not touching the elements at end of list that are
            //already placed in correct position

            //restore heap property without touching the elements that are already in the correct position
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
     * Copy elements from start index to end index in oldList to newList
     *
     * @param oldList list where elements will be copied from
     * @param newList list where elements will be copied to
     * @param start   first element in oldList that will be copied
     * @param end     last element in oldList that will be copied
     * @return newList with copied elements from oldList
     * @throws ATCSimException when the newList size smaller than number of elements that needs to be copied
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

    /**
     * Return airplane with greatest AC value
     *
     * @return airplane with greatest AC value
     */
    public Airplane heapMaximum() {
        //since first element in queue has the highest AC value, return first element
        return airplanesQueue.get(0);
    }

    /**
     * Remove and return airplane with greatest AC value
     *
     * @param planeList max heap where airplane will be extracted
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
        planeList.remove(planeList.size() - 1); //decrease heap size by 1
        maxHeapify(planeList, 0); //restore max-heap property
        return maxPriority; //return airplane that has greatest AC value
    }

    /**
     * Increase AC value of airplane and moves it to the correct position in heap
     *
     * @param index current index of airplane in heap
     * @param planeNewAC plane to be added to heap
     * @throws ATCSimException if new AC value is smaller than current AC value
     */
    public void heapIncreaseKey(int index, Airplane planeNewAC) throws ATCSimException {

        Airplane airplane = airplanesQueue.get(index); //current airplane in queue

        if (planeNewAC.getAC() < airplane.getAC()) { //if key is less than the current key
            throw new ATCSimException("New AC value is smaller than current key!");
        }

        //put planeNewAC into index i
        airplanesQueue.set (index, planeNewAC);

        //swap as long as the key is greater than the parent's key
        while (index > 0 && airplanesQueue.get(getParent(index)).getAC() < planeNewAC.getAC()) {
            swap(airplanesQueue, index, getParent(index));
            index = getParent(index);
        }
    }

    /**
     * Insert new airplane into max heap
     *
     * @param plane airplane to be inserted
     */
    public void maxHeapInsert(Airplane plane) {
        //inserts an airplane object with -1 value (value lower than all other AC values)
        Airplane lowestAC = new Airplane(-1);
        airplanesQueue.add(lowestAC); //size will automatically increase

        lowestAC = plane; //sets the plane with -1 AC value to plane that needs to be inserted
        try {
            //call heapIncrease key with the plane so it can be placed at the correct position
            heapIncreaseKey(airplanesQueue.size() - 1, lowestAC);
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        //Tells user that the plane has been successfully added to the priority queue
        System.out.println("Airplane with the following information: ");
        plane.printAirplaneInfo();
        System.out.println("has been successfully added to the airplane heap!");
    }

    /**
     * Extracts an airplane from queue for emergency landing
     */
    public void emergencyLanding (){
        printAirplaneList(airplanesQueue); //prints airplane queue so user can see all airplanes currently in line to land

        //allow users to choose airplane that they wan t to increase priority of
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the position number of the plane that you want to increase the priority of: ");
        int numberInQueue = scan.nextInt();
        int index = numberInQueue - 1; //Priority queue displays value from 1, subtract 1 to get index of Airplane in ArrayList

        //retrieve Airplane from priority queue
        Airplane toExtract = airplanesQueue.get(index);
        String flightNum = toExtract.getFlightNum();
        //set the AC value to a value so high that no other airplane can have
        toExtract.setAC(14000);

        try {
            heapIncreaseKey(index, toExtract);
            heapExtractMax(airplanesQueue);
        }
        catch (ATCSimException error){
            error.printStackTrace();
        }

        //heapExtractMax extracts the first plane in the queue out and restores the max-heap property
        //although airplanesqueue might be a maxheap, it might not be in the order from highest to lowest AC
        //Therefore, makePriorityQueue is called again to make sure the queue is in order from highest to lowestAC
        airplanesQueue = makePriorityQueue(airplanesQueue);
        int indexInAirplaneList = findPlane(flightNum, airplanesList);
        removeFromAirplaneList(indexInAirplaneList);

        System.out.println("Airplane with the following information has safely landed");
        toExtract.printAirplaneInfo();
    }

    /**
     * Lands the first airplane in queue
     */
    public void landFirstAirplane(){
        try {
            //extract the first airplane in queue
            Airplane extracted = heapExtractMax(getAirplaneQueue());
            airplanesQueue = makePriorityQueue(airplanesQueue);
            //find the airplane in airplane list and remove it
            int index = findPlane(extracted.getFlightNum(), getAirplaneList());
            removeFromAirplaneList(index);

            //print information of the plane that has been extrated
            System.out.println("The following airplane has landed: ");
            extracted.printAirplaneInfo();

        } catch (ATCSimException error) {
            error.printStackTrace();
        }
    }

    //user program
    public static void main(String[] args) {

        //Create a new ATCSim object that contains 30 randomly generated airplane objects
        ATCSim simulator = new ATCSim();
        Scanner scan = new Scanner(System.in);
        boolean done = false;

        //Print out menu that displays options users can choose from
        while (!done) {
            System.out.println();
            System.out.println ("Note: The airplanes in this program are printed in the following format:");
            System.out.println ("(flight number, D: distance in meters, H:elevation in meters) - AC: approach code");
            System.out.println();

            System.out.println("Please choose one of the following options by typing in the number from menu: ");
            System.out.println("1 -> Display Landing Sequence of Airplanes");
            System.out.println("2 -> Add an Airplane to the list and queue");
            System.out.println("3 -> View first airplane in queue to land");
            System.out.println("4 -> Increase an airplane priority in queue");
			System.out.println("5 -> Land the first Airplane in queue");
			System.out.println("6 -> Emergency: Choose an airplane to land immediately");

            boolean legitChoice = false; //indicates whether user has entered an legitimate option
            while (!legitChoice) {

                int choice = 0;
                try {
                    //use nextLine() because a user might type in letters rather than numbers and we wnat to read everything user types
                    String lineRead = scan.nextLine();
                    choice = Integer.parseInt(lineRead);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                switch (choice) {
                    //Display Landing Sequence
                    case 1:
                    	legitChoice = true;
                        System.out.println("Airplanes Landing sequence (sorted using Heapsort and printed backward): ");

                        //heapsort the list of airplanes and print the list in backwards order
                        simulator.printAirplaneListBackwards(simulator.heapsort(simulator.getAirplaneList()));
                        break;

                    //Add airplane to list and queue
                    case 2:
						legitChoice = true;

						boolean airplaneDoesNotExist = false;
						//Have user enter a flight number

                        String flightNumber = "";
                        while (!airplaneDoesNotExist) {
                            System.out.println("Please enter a Flight Number. Make Sure the Flight Number does not exist yet: ");
                            flightNumber = scan.nextLine();
                            if (simulator.findPlane(flightNumber, simulator.getAirplaneList()) == -1) {
                                airplaneDoesNotExist = true;
                            }
                        }
                        //Create a new Airplane object from the given
                        Airplane plane = new Airplane(flightNumber);

                        //Insert Airplane into the Priority Queue and Airplane List
                        simulator.insertQueue(plane);
                        simulator.addAirplane(plane);
                        break;

                    //View first airplane in queue
                    case 3:
                        legitChoice = true;

                        //Prints the information of the first airplane in the queue
                        System.out.println("First airplane in queue: ");
                        Airplane first = simulator.heapMaximum();
                        first.printAirplaneInfo();
                        break;

                    //increase priority of airplane in queue
                    case 4:
						legitChoice = true;

						//increases thee priority of the airplane in the queue
                        simulator.userIncreasePriority();

                        //Prints out the queue with the updated AC value included
                        System.out.println("Updated Airplane Queue: ");
                        simulator.printAirplaneList(simulator.getAirplaneQueue());
                        break;

                    // Land the first airplane in the queue
                    case 5:
						legitChoice = true;
                        simulator.landFirstAirplane();
                        break;

                    //Choose an airplane to land immediately
                    case 6:
                        legitChoice = true;
                        //increase priority of an airplane so it can land immediately
                        simulator.emergencyLanding();
                        break;

                    //if user enters a choice that doesn't exist
                    default:
                        System.out.println("Option does not exist, please enter another one");

                }
            }

            boolean correctResponse = false; //indicates whether the user input is the right format
            System.out.println("Is there another action you want to take? \n Enter \"YES\" or \"NO\"");
            while (!correctResponse) {
                String response = scan.nextLine();

                response = response.toUpperCase(); //change the response to ppercase (just in cse user enters in lwoercase letters)
                if (response.equals("NO")) {
                    correctResponse = true;
                    done = true;
                    System.out.println("Thank you. Simulation ends. Have a great day!");
                } else if (response.equals("YES")) {
                    correctResponse = true;
                } else {
                    System.out.println("Incorrect Input. Please make sure you are in the correct format. Please enter \"YES\" or \"NO\"");
                }
            }
        }
        scan.close();
    }

}
