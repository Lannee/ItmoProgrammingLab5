package main.java.src;

/**
 * Main class where program starts
 */
public class Main {
    /**
     * Program entering point
     * @param args args from command line
     */
    public static void main(String[] args) {
        Client client = new Client(args);
        client.runClient();
    }
}
