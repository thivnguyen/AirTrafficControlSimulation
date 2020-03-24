package ATCSimulator;
import java.util.*;

public class TestFunctions {
    public static void main (String [] args){

        //Test maxHeapify
        System.out.println ("Testing maxHeapify....");
        System.out.println ("Original Airplane List:");
        ATCSim sim1 = new ATCSim ();

        sim1.printAirplaneList(sim1.getAirplaneList());

        ArrayList <Airplane> sorted = sim1.maxHeapify (sim1.getAirplaneList(), 0);
        System.out.println ("Airplane List after maxHeapify is called:");
        sim1.printAirplaneList(sorted);
    }


}
