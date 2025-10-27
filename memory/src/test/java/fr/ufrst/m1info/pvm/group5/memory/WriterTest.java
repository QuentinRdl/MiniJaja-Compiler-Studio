package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    Writer writer;
    boolean flags[];

    @BeforeEach
    public void setUpWriter() {
        writer = new Writer();
        // Flags that updates when events are triggered
        flags = new boolean[]{false, false, false}; // 0 -> text added; 1 -> text removed; 2 -> text changed
        writer.TextAddedEvent.subscribe(event -> {flags[0] = true;});
        writer.TextRemovedEvent.subscribe(event -> {flags[1] = true;});
        writer.TextChangedEvent.subscribe(event -> {flags[2] = true;});
    }

    /*
     * The following 4 tests ensure the right event are triggered by the method calls
     */

    @Test
    @DisplayName("Event trigger - writeAsync")
    public void EventTrigger_Write() throws Exception{
        var future = writer.writeAsync("a");
        future.get();
        assertTrue(flags[0]);
        assertFalse(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - Writeline")
    public void EventTrigger_Writeline() throws Exception{
        var future = writer.writeLineAsync("a");
        future.get();
        assertTrue(flags[0]);
        assertFalse(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - Erase")
    public void EventTrigger_Erase() throws Exception{
        var future = writer.eraseAsync(5);
        future.get();
        assertFalse(flags[0]);
        assertTrue(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - EraseLine")
    public void EventTrigger_EraseLine() throws Exception{
        var future = writer.eraseLineAsync();
        future.get();
        assertFalse(flags[0]);
        assertTrue(flags[1]);
        assertTrue(flags[2]);
    }

    /*
     * The following tests are there to ensure the information given by the events are correct
     */

    @Test
    @DisplayName("Event info - Write / Text added event")
    public void EventInfo_Write_Textadded() throws Exception{
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("hello world", e.diff());
            assertEquals(11, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("", e.oldText());
        });
        var future = writer.writeAsync("hello world");
        future.get();
    }

    @Test
    @DisplayName("Event info - Write / Text added event / with text present")
    public void EventInfo_Write_Textadded2() throws Exception{
        var future = writer.writeAsync("hello ");
        future.get();
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals(5, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("hello", e.oldText());
        });
        future = writer.writeAsync("world");
        future.get();
    }

    @Test
    @DisplayName("Event info - Write / Text changed event")
    public void EventInfo_Write_TextChanged() throws Exception{
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("hello world", e.diff());
            assertEquals(11, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        var future = writer.writeAsync("hello world");
        future.get();
    }

    @Test
    @DisplayName("Event info - Write / Text changed event / with text present")
    public void EventInfo_Write_TextChanged2() throws Exception{
        var future = writer.writeAsync("hello ");
        future.get();
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals(5, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("hello", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        future = writer.writeAsync("world");
        future.get();
    }


}
