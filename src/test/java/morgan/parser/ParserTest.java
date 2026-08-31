package morgan.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import morgan.exception.MorganException;
import morgan.storage.Storage;
import morgan.task.TaskList;
import morgan.ui.Ui;

public class ParserTest {

    @Test
    public void parseAndExecute_byeCommand_returnsTrue() throws MorganException {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test.txt");

        boolean isExit = Parser.parseAndExecute("bye", tasks, ui, storage);
        assertTrue(isExit);
    }

    @Test
    public void parseAndExecute_invalidCommand_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test.txt");

        MorganException exception = assertThrows(MorganException.class, () -> {
            Parser.parseAndExecute("invalidCmd", tasks, ui, storage);
        });


        assertEquals("Meow? Is that a fish?", exception.getMessage());
    }

    @Test
    public void parseAndExecute_emptyTodoDescription_exceptionThrown() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test.txt");

        MorganException exception = assertThrows(MorganException.class, () -> {
            Parser.parseAndExecute("todo ", tasks, ui, storage);
        });

        assertEquals("Meow? Is that a fish?", exception.getMessage());
    }
}
