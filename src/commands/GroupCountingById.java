package main.java.src.commands;

import main.java.src.Client;
import main.java.src.logic.data.Receiver;

import java.util.Map;

/**
 * Groups the elements of the collection by the value of the id field, displays the number of elements in each group
 */
public class GroupCountingById implements Command {
    /**
     * array of command arguments
     */
    private static final String[] args = new String[0];

    private final Receiver receiver;

    /**
     * Command constructor
     * @param receiver receiver to get data from and group
     */
    public GroupCountingById(Receiver receiver) {
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
            Map<Object, Integer> groups = receiver.groupByField("id");
            groups.forEach((u, v) -> Client.out.print(u + " : " + v + "\n"));
        } catch (NoSuchFieldException e) {
            Client.out.print("Stored type does not support this command\n");
        }
    }

    /**
     * returns command description
     * @return returns command description
     */
    @Override
    public String getDescription() {
        return "Groups the elements of the collection by the value of the id field, displays the number of elements in each group";
    }

    /**
     * returns command args
     * @return returns command args
     */
    @Override
    public String[] args() {
        return args;
    }
}
