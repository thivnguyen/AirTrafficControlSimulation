package ATCSimulator;
import java.util.*;

public class TestFunctions {
    public static void main (String [] args){


        //Test maxHeapify
        System.out.println ("Testing maxHeapify....");
        System.out.println ("Original Airplane List:");
        ATCSim sim1 = new ATCSim ();

        sim1.printAirplaneList(sim1.getAirplaneList());

        ArrayList <Airplane> heapify = sim1.maxHeapify (sim1.getAirplaneList(),2);
        System.out.println ("Airplane List after maxHeapify is called:");
        sim1.printAirplaneList(heapify);

        //Test Build-Max-Heap
        System.out.println ("Testing buildMaxHeap....");
        System.out.println ("Original Airplane List:");
        ATCSim sim2 = new ATCSim ();

        sim2.printAirplaneList(sim2.getAirplaneList());

        ArrayList <Airplane> heap = sim2.buildMaxHeap (sim2.getAirplaneList());
        System.out.println ("Airplane List after buildMaxHeap is called:");
        sim2.printAirplaneList(heap);

        //Test Heapsort
        System.out.println ("Testing heapsort....");
        System.out.println ("Original Airplane List:");
        ATCSim sim3 = new ATCSim ();

        sim3.printAirplaneList(sim3.getAirplaneList());

        ArrayList<Airplane> sorted = sim3.heapSort(sim3.getAirplaneList());
        System.out.println ("Airplane List after heapsort is called:");
        sim3.printAirplaneList(sorted);

        for (int i = 0; i < 10; i++) {
            System.out.println(sim3.generateFlightNum());
        }

    }
}

