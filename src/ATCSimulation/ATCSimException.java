package ATCSimulation;

/**
 *Thrown when an input is preventing ATC Simulator program to execute smoothly
 */
public class ATCSimException extends Exception{

    /**
     * Constructs an ATCSimException with a specified message
     * @param message detailed message
     */
    public ATCSimException(String message){
        super (message);
    }
}
