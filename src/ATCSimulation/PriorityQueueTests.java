package ATCSimulation;

import java.util.ArrayList;
import java.util.Random;

public class PriorityQueueTests{

    private ArrayList<Airplane> airplanesList;
    private ArrayList<Airplane> airplanesQueue;

    /**
     * Constructs 30 Airplane Objects and stores into an airplane list and an airplane priority queue
     */
    public PriorityQueueTests() {
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
     * Create a Priority Queue given a maxheap
     *
     * @param maxHeap maxHeap that Priority Queue will be created from
     * @return priority queue
     */
    public ArrayList<Airplane> makePriorityQueue(ArrayList<Airplane> maxHeap) {

        System.out.println();
        //System.out.println ("Entering makePriorityQueue method");
        ArrayList<Airplane> queue = new ArrayList<Airplane>();
        int size = maxHeap.size();

        //Keep extracting the first Node of the maxHeap util there is none left
       // System.out.println("Will use heapExtractMax() to keep extracting the max node from the max heap and adding to queue");
        for (int i = 0; i < size; i++) {
            try {
                queue.add(heapExtractMaxNP(maxHeap));
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        return queue;
    }

    /**
     * Create a Priority Queue given a maxheap with printed outputs
     *
     * @param maxHeap maxHeap that Priority Queue will be created from
     * @return priority queue
     */
    public ArrayList<Airplane> makePriorityQueueP(ArrayList<Airplane> maxHeap) {

        System.out.println();
        //System.out.println ("Entering makePriorityQueue method");
        ArrayList<Airplane> queue = new ArrayList<Airplane>();
        int size = maxHeap.size();

        //Keep extracting the first Node of the maxHeap util there is none left
        // System.out.println("Will use heapExtractMax() to keep extracting the max node from the max heap and adding to queue");
        for (int i = 0; i < size; i++) {
            try {
                Airplane extracted = heapExtractMaxNP(maxHeap);
                System.out.println ("Extracting airplane with AC (" + extracted.getAC() + ") and adding to priority queue");
                queue.add(extracted);
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        return queue;
    }

    /**
     * Print the Airplane List starting from 1
     *
     * @param planeList Airplane List to be printed
     */
    public void printAirplaneList(ArrayList<Airplane> planeList) {
        int counter = 1; // counter will indicate position of Airplane in Queue when printed
        for (Airplane a : planeList) {
            System.out.print(counter + ". ");
            a.printAirplaneInfo();
            counter++;
        }
    }

    /**
     * Print the Airplane List starting from 0
     *
     * @param planeList Airplane List to be printed
     */
    public void printAirplaneListZero(ArrayList<Airplane> planeList) {
        int counter = 0; // counter will indicate position of Airplane in Queue when printed
        for (Airplane a : planeList) {
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
     * Move node @ index i to correct position in Heap so that the max-heap property can be maintained
     *
     * @param planeList Heap where node currently is at
     * @param i         index of node that will be moved to right position
     */
    public void maxHeapifyP(ArrayList<Airplane> planeList, int i) {

        System.out.println();
        System.out.println ("Entering maxHeapify() method");
        int left = getLeft(i); //index of node to the left
        if (left <= getHeapSize(planeList) - 1) {
            System.out.println("Airplane on left node (index "+ left + ") has AC value -> " + planeList.get(left).getAC());
        }
        else{
            System.out.println ("No airplane on left node");
        }
        int right = getRight(i); //index of node to the right
        if (right <= getHeapSize(planeList) - 1 ){
            System.out.println ("Airplane on right node (index "+ right + ") has AC value -> "+ planeList.get(right).getAC());
        }
        else{
            System.out.println ("No airplane on right node");
        }

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
            System.out.println ("Swapping airplane @ index " + i + " (AC: "+ planeList.get(i).getAC() + ") and airplane @ index " + largest + " (AC: "+ planeList.get(largest).getAC() +")");
            swap(planeList, i, largest);
//            System.out.println ("Airplane List after swap: ");
//            printAirplaneList(planeList);
            //call maxheapify on the node that has the largest value between the node
            //the node's left node, and the node's right node
            System.out.println ("Maxheapify called again on index " + largest + " (AC: "+ planeList.get(largest).getAC() +")");
            maxHeapifyP(planeList, largest);

        }
        else{
            System.out.println ("No more swapping needed because Airplane already has higher AC value than its children (if there are any)");
            System.out.println();
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
        System.out.println();
        System.out.println("Entering heapExtractMax() method");
        //checks if list is empty
        if (getHeapSize(planeList) < 1) {
            throw new ATCSimException("heap underflow");
        }

        Airplane maxPriority = planeList.get(0);
        System.out.println ("Plane @ root in heap is ");
        maxPriority.printAirplaneInfo();

        System.out.println("Move last airplane of heap to location of extracted airplane and decreasing heap size by 1");
        planeList.set(0, planeList.get(planeList.size() - 1)); //move last element to the front of the list
        planeList.remove(planeList.size() - 1);
        printAirplaneListZero(planeList);

        System.out.println("Using maxHeapify on airplane @ index 0 to restore Max-Heap-Property");
        maxHeapify(planeList, 0); //restore max-heap property
        System.out.println ();
        System.out.print("Airplane has been successfully extracted: ");
        maxPriority.printAirplaneInfo();

        return maxPriority; //return Airplane that has greatest AC value
    }

    /**
     * Removes and returns the Airplane with the greatest AC value (does not print output per step)
     *
     * @param planeList maxheap where airplane wil be extracted
     * @return Airplane that was extracted
     * @throws ATCSimException if list is empty
     */
    public Airplane heapExtractMaxNP(ArrayList<Airplane> planeList) throws ATCSimException {

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
     * @param plane plane with new AC value for the airplane
     * @return new index of airplane
     * @throws ATCSimException if new AC value is not large enough to be moved up in max-heap
     */
    public void heapIncreaseKey(int index, Airplane plane) throws ATCSimException {

        System.out.println();
        System.out.println("Entering/Testing Heap Increase Key() method");

        Airplane airplane = airplanesQueue.get(index);

        System.out.println ("Old AC value: " + airplane.getAC());
        System.out.println ("New AC value: " + plane.getAC());

        if (plane.getAC() < airplane.getAC()) { //if key is less than the current key
            throw new ATCSimException("New AC value is too smaller than current key!");
        }

        //put plane into index i
        airplanesQueue.set (index, plane);

        //swap as long as the key is greater than the parent's key
       System.out.println ("Keep swapping airplane with its parent node as long as the parent has a lower AC value");
        while (index > 0 && airplanesQueue.get(getParent(index)).getAC() < plane.getAC()) {
            System.out.println ("Current airplanes's AC value: " + plane.getAC());
            System.out.println ("Parent Node's AC value: "+ airplanesQueue.get(getParent(index)).getAC());
            System.out.println ("Swapped airplane and its parent");
            swap(airplanesQueue, index, getParent(index));
            index = getParent(index);
        }

        System.out.println ("Done swapping. Airplane is now in the right location in heap");
    }

    /**
     * Inserts new Airplane into the Priority Queue
     *
     * @param plane airplane to be inserted
     */
    public void maxHeapInsert(Airplane plane) {
        //inserts an airplane object with -1 value (value lower than all other AC values)
        System.out.println ("Add plane with AC value of -1 into end of Heap. (Note that this plane doesn't have a flight number, distance, or elevation , because it is a \"dummy\"" +
                " airplane object");
        Airplane lowestAC = new Airplane(-1);
        airplanesQueue.add(lowestAC); //size will automatically increase

        System.out.println("Airplane list after Airplane with -1 AC value added");
        printAirplaneListZero(airplanesQueue);

        System.out.println("Setting airplane that will be added to Airplane with -1 AC value");
        lowestAC = plane; //sets the plane with -1 AC value to plane that needs to be inserted
        try {
            //call heapIncrease key with the plane so it can be placed at the correct position
            System.out.println("Calling heapIncreaseKey to move Airplane to correct location in the heap");
            heapIncreaseKey(airplanesQueue.size() - 1, lowestAC);
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        System.out.println ("After inserting plane into the heap: ");
        printAirplaneListZero(getAirplaneQueue());

        //Tells user that the plane has been successfully added to the priority queue
        System.out.println("Airplane with the following information: ");
        plane.printAirplaneInfo();
        System.out.println("has been successfully added to the airplane heap!");
    }

    public static void main (String[]args) {

        PriorityQueueTests sim1 = new PriorityQueueTests();
        System.out.println("Testing Heap Extract Max");
        System.out.println("Max Heap: ");
        ArrayList<Airplane> maxHeap = sim1.buildMaxHeap(sim1.getAirplaneList());
        sim1.printAirplaneListZero(maxHeap);
        System.out.println();

        try {
            sim1.heapExtractMax(maxHeap);
        } catch (ATCSimException error) {
            error.printStackTrace();
        }

        System.out.println("Max Heap after heapExtractMax() has been called:");
        sim1.printAirplaneListZero(maxHeap);

        PriorityQueueTests sim2 = new PriorityQueueTests();
        System.out.println();
        System.out.println ("Testing HeapMaximum");
        System.out.println ("Max Heap (Priority Queue): ");
        sim2.printAirplaneListZero(sim2.getAirplaneQueue());
        System.out.println();

        Airplane maximum = sim2.heapMaximum();
        System.out.println("Airplane with maximum AC value in maxheap: ");
        maximum.printAirplaneInfo();


        System.out.println();
        System.out.println ("Testing maxHeapInsert()");
        Airplane newPlane = new Airplane ("HI132");
        System.out.print("Inserting: ");
        newPlane.printAirplaneInfo();
        sim2.maxHeapInsert(newPlane);

        int index = sim2.findPlane(newPlane.getFlightNum(), sim2.getAirplaneQueue());
        int parent = sim2.getParent(index);
        int parentsAC = sim2.getAirplaneQueue().get(parent).getAC();

        System.out.println ("AC of Parent's Node: " + parentsAC);
        if (parentsAC > newPlane.getAC()){
            System.out.println ("maxHeapInsert() is successful because Airplane is in correct position.");
            System.out.println("Please note that this method inserts the plane into the right location in the max heap, not the priority queue.\n Therefore, " +
                    "the airplanes are not ordered from greatest to smallest AC");
        }


    }
}
