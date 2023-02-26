package main.java.src.commands;

import main.java.src.Program;
import main.java.src.logic.data.DataManager;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

public class CountGreaterThanWeight implements Command {

    private final static String[] args = {"weight"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());

        DataManager<?> collection = program.collection;
        AtomicLong counter = new AtomicLong();
        try {
            Field weightField = collection.getClT().getDeclaredField(args()[0]);
            weightField.setAccessible(true);
            Comparable givenValue = (Comparable) StringConverter.methodForType.get(weightField.getType()).apply(args[0]);
            collection.forEach(e -> {
                try {
                    if(givenValue.compareTo((Comparable)weightField.get(e)) < 0) counter.getAndIncrement();
                } catch (IllegalAccessException impossible) { }
            });

            program.out.print(counter + "\n");

        } catch (NoSuchFieldException e) {
            program.out.print("Stored type does not have " + args()[0] + " field\n");
        } catch (NumberFormatException e) {
            program.out.print("Incorrect given value\n");
        }

    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public String getDescription() {
        return "Print the number of elements whose " + args[0] + " field value is greater than the specified one";
    }
}
