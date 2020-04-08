package ATCSimulation;

import java.util.ArrayList;
import java.util.Random;

public class HeapSortTests {

    private ArrayList<Airplane> airplanesList;

    /**
     * Constructs 30 Airplane Objects and stores into an airplane list
     */
    public HeapSortTests() {
        //Creates 30 Airplane objects
        airplanesList = new ArrayList<Airplane>();
        for (int i = 1; i <= 30; i++) {
            airplanesList.add(new Airplane(generateFlightNum())); //generate FlightName randomly
        }
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
     * Print the Airplane List
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
     * Print the Airplane List
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

        System.out.println ("Entering/Testing maxHeapify() method on node @ index " + i + " (AC: " + planeList.get(i).getAC() + ")");
        int left = getLeft(i); //index of node to left
        if (left <= getHeapSize(planeList) - 1) {
            System.out.println("Airplane on left node (index "+ left + ") has AC value -> " + planeList.get(left).getAC());
        }
        else{
            System.out.println ("No airplane on left node");
        }
        int right = getRight(i); //index of node to right
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
            //swap node i with the node that has the largest value in order to move the node with a larger value up and node i down
            System.out.println ("Swap airplane @ index " + i + " (AC: "+ planeList.get(i).getAC() + ") and airplane @ index " + largest + " (AC: "+
                    planeList.get(largest).getAC() +")");
            swap(planeList, i, largest);

            //call maxheapify on the node that has the largest value between node, node's left node, and node's right node
            System.out.println ("Maxheapify called again on index " + largest + " (AC: "+ planeList.get(largest).getAC() +")");
            maxHeapify(planeList, largest);
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
        System.out.println();
        System.out.println ("Entering/Testing buildMaxHeap() method");
        ArrayList<Airplane> airplaneHeap = copyList(planeList);

        //call maxHeapify on each element in a bottom-up manner
        System.out.println ("buildMaxHeap works by calling maxHeapify on indexes from "+ (getHeapSize(airplaneHeap) / 2 - 1) + " to 0");
        for (int i = getHeapSize(airplaneHeap) / 2 - 1; i >= 0; i--) {
            System.out.println ("Calling maxHeapify for airplane at index " + i + " (AC value -> " + airplaneHeap.get(i).getAC() +")");
            System.out.println();
            maxHeapify(airplaneHeap, i); //maxHeapify the node so that it will go to the right place in the heap
        }
        System.out.println ("Airplane Heap after calling buildMaxHeap method");
        printAirplaneListZero(airplaneHeap);
        return airplaneHeap;
    }

    /**
     * Sorts Airplanes from lowest to greatest AC value
     *
     * @param planeList Airplane list to be sorted
     * @return sorted list of Airplanes from lowest to highest AC value
     */
    public ArrayList<Airplane> heapsort(ArrayList<Airplane> planeList) {
        System.out.println();
        System.out.println ("Entering heapsort() method");
        System.out.println ("Calling buildMaxHeap on airplane list to create a maxHeap from Airplane List");
        System.out.println();
        ArrayList<Airplane> heap = buildMaxHeap(planeList); //create a maxHeap from the given planeList
        System.out.println();

        System.out.println("After max heap has been built: ");
        for (int i = heap.size() - 1; i > 0; i--) {
            //move maximum element to the it's correct position by swapping
            System.out.println ("Swapping airplane with max AC code (airplane @ index 0) with airplane @ index "+ i);
            swap(heap, 0, i);

            //Restore heap property without touching the elements that are already in teh correct position
            ArrayList<Airplane> subList = new ArrayList<Airplane>();
            try {
                //copy elements from 0 to i-1 to another list
                createSublist(subList, heap, 0, i - 1);
                //use maxheapify to restore the max-heap property
                System.out.println ("Call maxheapify on the airplane @ index 0 (AC: "+ subList.get (0).getAC() +") to restore max heap property");
                System.out.println();
                maxHeapify(subList, 0);
                System.out.println ("Heap after max-heap property has been restored on airplanes from indexes 0 to " + (i-1) + "," +
                        " pay attention to index 0 to "+ (i-1) + ". Airplanes from index " + i + " to index " + (heap.size()-1)+ " has already been sorted.");
                //add the elements back to the original list
                createSublist(heap, subList, 0, i - 1);
                printAirplaneListZero(heap);
                System.out.println();
            } catch (ATCSimException error) {
                error.printStackTrace();
            }
        }
        System.out.println ("Airplane list after heapsort has been called");
        printAirplaneList(heap);
        return heap;
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

        HeapSortTests sim1 = new HeapSortTests();

        System.out.println ("Original Airplane List");
        sim1.printAirplaneListZero(sim1.getAirplaneList());
        System.out.println();

        System.out.println("Testing heapsort()");
        System.out.println ("Calling heapsort() on Airplane List");
        System.out.println ();
        sim1.heapsort(sim1.getAirplaneList());
        System.out.println();

    }
}
