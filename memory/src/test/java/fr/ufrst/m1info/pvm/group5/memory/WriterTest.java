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

    /**
     * The following tests ensure the right event are triggered by the method calls
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
}
