package main.java.src.commands;

import main.java.src.Program;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RemoveById implements Command {

    private static final String[] args = {"id"};
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());
        Long id;
        try {
            id = (Long) StringConverter.methodForType.get(Long.class).apply(args[0]);
        } catch (NumberFormatException e) {
            program.out.print("Incorrect argument value\n");
            return;
        }

        Field idField;
        try {
            idField = program.collection.getClT().getDeclaredField(args()[0]);
        } catch (NoSuchFieldException e) {
            program.out.print("Stored type does not support this command\n");
            return;
        }

        idField.setAccessible(true);

        AtomicReference<Object> element = new AtomicReference<>();
        program.collection.forEach(e -> {
            try {
                if(idField.get(e).equals(id)) {
                    element.set(e);
                }
            } catch (IllegalAccessException ex) {}
        });

        if(program.collection.remove(element.get())) {}
        else {
            program.out.print("Unable to remove object\n");
        }
    }

    @Override
    public String getDescription() {
        return "Removes an item from the collection by its " + args[0];
    }

    @Override
    public String[] args() {
        return args;
    }
}
