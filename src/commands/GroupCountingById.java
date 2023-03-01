package main.java.src.commands;

import main.java.src.Program;
import main.java.src.utils.StringConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Groups the elements of the collection by the value of the id field, displays the number of elements in each group
 */
public class GroupCountingById implements Command {
    private static final String[] args = new String[0];
    @Override
    public void execute(String[] args) {
        Program program = Program.getInstance();
        Command.checkArgsConformity(args, args());

        Map<Long, Integer> groups = new HashMap<>();

        Field idField;
        try {
            idField = program.collection.getClT().getDeclaredField("id");
            idField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            program.out.print("Stored type does not support this command\n");
            return;
        }

        Long id;
        for(Object element : program.collection.getElements()) {
            try {
                id = idField.getLong(element);
                if(groups.containsKey(id)) {
                    Integer value = groups.get(id);
                    groups.put(id, ++value);
                } else {
                    groups.put(id, 1);
                }

            } catch (IllegalAccessException e) {}
        }

        groups.forEach((u, v) -> program.out.print(u + " : " + v + "\n"));
    }

    @Override
    public String getDescription() {
        return "Groups the elements of the collection by the value of the id field, displays the number of elements in each group";
    }

    @Override
    public String[] args() {
        return args;
    }
}
