package main.java.src.commands;

import main.java.src.Program;
import main.java.src.utils.ObjectFactory;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Update implements Command {
    public final static String[] args = {"id"};

    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args(), args);

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
            idField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            program.out.print("Stored type does not support this command\n");
            return;
        }


        boolean isIdPresent = false;
        Object obj = new Object();
        for(Object e : program.collection.getElements()) {
            try {
                if(idField.get(e).equals(id)) {
                    isIdPresent = true;
                    obj = e;
                    break;
                }
            } catch (IllegalAccessException ex) {}
        }

        if(!isIdPresent) {
            program.out.print("Element with is id does not exist, do you want to create it (y/n) : ");
            if(!program.in.readLine().equals("y")) {
                return;
            }
        }

        program.collection.remove(obj);
        obj = ObjectFactory.createObjectInteractively(program.collection.getClT());
        try {
            idField.set(obj, id);
        } catch (IllegalAccessException e) {}

        program.collection.add(program.collection.getClT().cast(obj));
        program.out.print("Element with " + args[0] + " " + id + " was successfully updated\n");
    }

    @Override
    public String getDescription() {
        return  "Updates the value of a collection item whose " + args[0] + " is equal to the specified one";
    }

    @Override
    public String[] args() {
        return args;
    }
}
