package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

/**
 * Print the number of elements whose weight field value is greater than the specified one
 */
public class CountGreaterThanWeight implements Command {

    /**
     * array of command arguments
     */
    private final static String[] args = {"weight"};

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to count in
     */
    public CountGreaterThanWeight(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Method executing the command
     * @param args command arguments to execute with
     */
    @Override
    public void execute(String[] args) {
        checkArgsConformity(args);
        try {
            int amount = receiver.countCompareToValueByField(args()[0], args[0], (u, v) -> -u.compareTo(v));
            Client.out.print(amount + "\n");
        } catch (NumberFormatException e) {
            Client.out.print("Incorrect given value\n");
        }
    }

    /**
     * returns command args
     * @return returns command args
     */
    @Override
    public String[] args() {
        return args;
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Print the number of elements whose " + args[0] + " field value is greater than the specified one";
    }
}
