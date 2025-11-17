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
     * The following 5 tests ensure the right event are triggered by the method calls
     */

    @Test
    @DisplayName("Event trigger - write")
    void EventTrigger_Write() throws Exception{
        writer.write("a");
        
        assertTrue(flags[0]);
        assertFalse(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - Writeline")
    void EventTrigger_Writeline() throws Exception{
        writer.writeLine("a");
        assertTrue(flags[0]);
        assertFalse(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - Erase")
    void EventTrigger_Erase() throws Exception{
        writer.erase(5);
        
        assertFalse(flags[0]);
        assertTrue(flags[1]);
        assertTrue(flags[2]);
    }

    @Test
    @DisplayName("Event trigger - EraseLine")
    void EventTrigger_EraseLine() throws Exception{
        writer.eraseLineAsync();
        
        assertFalse(flags[0]);
        assertTrue(flags[1]);
        assertTrue(flags[2]);
    }

    /*
     * The following tests are there to ensure the information given by the events are correct
     */

    @Test
    @DisplayName("Event info - Write / Text added event")
    void EventInfo_Write_Textadded() throws Exception{
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("hello world", e.diff());
            assertEquals(11, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("", e.oldText());
        });
        writer.write("hello world");
        
    }

    @Test
    @DisplayName("Event info - Write / Text added event / with text present")
    void EventInfo_Write_Textadded2() throws Exception{
        writer.write("hello ");
        
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals(5, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("hello ", e.oldText());
        });
        writer.write("world");
        
    }

    @Test
    @DisplayName("Event info - Write / Text changed event")
    void EventInfo_Write_TextChanged() throws Exception{
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("hello world", e.diff());
            assertEquals(11, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        writer.write("hello world");
        
    }

    @Test
    @DisplayName("Event info - Write / Text changed event / with text present")
    void EventInfo_Write_TextChanged2() throws Exception{
        writer.write("hello ");
        
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals(5, e.nbAdded());
            assertEquals("hello world", e.newText());
            assertEquals("hello ", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        writer.write("world");
        
    }

    @Test
    @DisplayName("Event info - Writeline / Text added event")
    void EventInfo_WriteLine_Textadded() throws Exception{
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("hello world\n", e.diff());
            assertEquals(12, e.nbAdded());
            assertEquals("hello world\n", e.newText());
            assertEquals("", e.oldText());
        });
        writer.writeLine("hello world");
        
    }

    @Test
    @DisplayName("Event info - WriteLine / Text added event / with text present")
    void EventInfo_WriteLine_Textadded2() throws Exception{
        writer.write("hello ");
        
        writer.TextAddedEvent.subscribe(e -> {
            assertEquals("world\n", e.diff());
            assertEquals(6, e.nbAdded());
            assertEquals("hello world\n", e.newText());
            assertEquals("hello ", e.oldText());
        });
        writer.writeLine("world");
        
    }

    @Test
    @DisplayName("Event info - WriteLine / Text changed event")
    void EventInfo_WriteLine_TextChanged() throws Exception{
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("hello world\n", e.diff());
            assertEquals(12, e.nbAdded());
            assertEquals("hello world\n", e.newText());
            assertEquals("", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        writer.writeLine("hello world");
        
    }

    @Test
    @DisplayName("Event info - Write / Text changed event / with text present")
    void EventInfo_WriteLine_TextChanged2() throws Exception{
        writer.write("hello ");
        
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("world\n", e.diff());
            assertEquals(6, e.nbAdded());
            assertEquals("hello world\n", e.newText());
            assertEquals("hello ", e.oldText());
            assertEquals(Writer.TextChangeEvent.TEXT_ADDED, e.change());
        });
        writer.writeLine("world");
        
    }

    @Test
    @DisplayName("Event info - Erase / no text")
    void EventInfo_Erase_NoText() throws Exception{
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("", e.diff());
            assertEquals("", e.oldText());
            assertEquals("", e.newText());
            assertEquals(0, e.nbRemoved());
        });
        writer.erase(10);
        
    }

    @Test
    @DisplayName("Event info - Erase / Text removed event / more text than erased")
    void EventInfo_Erase_TextRemoved_tooMuch() throws Exception{
        writer.write("Hello world");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("Hello ", e.newText());
            assertEquals(5, e.nbRemoved());
        });
        writer.erase(5);
        
    }

    @Test
    @DisplayName("Event info - Erase / Text removed event / more text than erased")
    void EventInfo_Erase_TextChanged_tooMuch() throws Exception{
        writer.write("Hello world");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("Hello ", e.newText());
            assertEquals(-5, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.erase(5);
        
    }

    @Test
    @DisplayName("Event info - Erase / Text removed event / less text than erased")
    void EventInfo_Erase_TextRemoved_notEnough() throws Exception{
        writer.write("Hello world");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("Hello world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("", e.newText());
            assertEquals(11, e.nbRemoved());
        });
        writer.erase(20);
        
    }

    @Test
    @DisplayName("Event info - Erase / Text changed event / less text than erased")
    void EventInfo_Erase_TextChanged_notEnough() throws Exception{
        writer.write("Hello world");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("Hello world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("", e.newText());
            assertEquals(-11, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.erase(20);
        
    }

    @Test
    @DisplayName("Event info - EraseLine / no text")
    void EventInfo_EraseLine_NoText() throws Exception{
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("", e.diff());
            assertEquals("", e.oldText());
            assertEquals("", e.newText());
            assertEquals(0, e.nbRemoved());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text removed event / oneLine")
    void EventInfo_Erase_TextRemoved_oneLine() throws Exception{
        writer.write("Hello world");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("Hello world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("", e.newText());
            assertEquals(11, e.nbRemoved());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text changed event / oneLine")
    void EventInfo_Erase_TextChanged_oneLine() throws Exception{
        writer.write("Hello world");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("Hello world", e.diff());
            assertEquals("Hello world", e.oldText());
            assertEquals("", e.newText());
            assertEquals(-11, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text removed event / multipleLines")
    void EventInfo_Erase_TextRemoved_moreLines() throws Exception{
        writer.write("Hello world\ndlrow olleH");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("\ndlrow olleH", e.diff());
            assertEquals("Hello world\ndlrow olleH", e.oldText());
            assertEquals("Hello world", e.newText());
            assertEquals(12, e.nbRemoved());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text changed event / multipleLines")
    void EventInfo_Erase_TextChanged_moreLines() throws Exception{
        writer.write("Hello world\ndlrow olleH");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("\ndlrow olleH", e.diff());
            assertEquals("Hello world\ndlrow olleH", e.oldText());
            assertEquals("Hello world", e.newText());
            assertEquals(-12, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text removed event / emptyLastLine")
    void EventInfo_Erase_TextRemoved_noLastLine() throws Exception{
        writer.write("Hello world\n");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("\n", e.diff());
            assertEquals("Hello world\n", e.oldText());
            assertEquals("Hello world", e.newText());
            assertEquals(1, e.nbRemoved());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text changed event / emptyLastLine")
    void EventInfo_Erase_TextChanged_noLastLine() throws Exception{
        writer.write("Hello world\n");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("\n", e.diff());
            assertEquals("Hello world\n", e.oldText());
            assertEquals("Hello world", e.newText());
            assertEquals(-1, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text removed event / onlyEmptyLastLine")
    void EventInfo_Erase_TextRemoved_noLastLine2() throws Exception{
        writer.write("\n");
        writer.TextRemovedEvent.subscribe(e -> {
            assertEquals("\n", e.diff());
            assertEquals("\n", e.oldText());
            assertEquals("", e.newText());
            assertEquals(1, e.nbRemoved());
        });
        writer.eraseLineAsync();
        
    }

    @Test
    @DisplayName("Event info - EraseLine / Text changed event / onlyEmptyLastLine")
    void EventInfo_Erase_TextChanged_noLastLine2() throws Exception{
        writer.write("\n");
        writer.TextChangedEvent.subscribe(e -> {
            assertEquals("\n", e.diff());
            assertEquals("\n", e.oldText());
            assertEquals("", e.newText());
            assertEquals(-1, e.nbAdded());
            assertEquals(Writer.TextChangeEvent.TEXT_REMOVED, e.change());
        });
        writer.eraseLineAsync();
        
    }
}
