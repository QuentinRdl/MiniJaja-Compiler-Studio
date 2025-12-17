package fr.ufrst.m1info.pvm.group5.ast;
import fr.ufrst.m1info.pvm.group5.ast.instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.ast.ASTMocks.Pair;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


class JajaCodeTest {
    @Mock
    Memory memory;
    Stack<Pair<String, Value>> storage;

    @BeforeEach
    public void setup(){
        storage = new Stack<>();
        memory = ASTMocks.createMemoryWithStack(storage);
    }

    @Test
    void fromFile_simple() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Simple.jjc");
        assertEquals(4, instrs.size());
        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof PushInstruction);
        assertTrue(instrs.get(2) instanceof PopInstruction);
        assertTrue(instrs.get(3) instanceof JcstopInstruction);
        for(int i = 0; i < instrs.size(); i++){
            assertEquals(i+1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_writeBasicOperations() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/WriteBasicOperations.jjc");
        assertEquals(21, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof NewInstruction);
        assertTrue(instrs.get(2) instanceof PushInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        assertTrue(instrs.get(4) instanceof AddInstruction);
        assertTrue(instrs.get(5) instanceof StoreInstruction);
        assertTrue(instrs.get(6) instanceof PushInstruction);
        assertTrue(instrs.get(7) instanceof WriteInstruction);
        assertTrue(instrs.get(8) instanceof LoadInstruction);
        assertTrue(instrs.get(9) instanceof WritelnInstruction);
        assertTrue(instrs.get(10) instanceof PushInstruction);
        assertTrue(instrs.get(11) instanceof IncInstruction);
        assertTrue(instrs.get(12) instanceof PushInstruction);
        assertTrue(instrs.get(13) instanceof WriteInstruction);
        assertTrue(instrs.get(14) instanceof LoadInstruction);
        assertTrue(instrs.get(15) instanceof WritelnInstruction);
        assertTrue(instrs.get(16) instanceof PushInstruction);
        assertTrue(instrs.get(17) instanceof SwapInstruction);
        assertTrue(instrs.get(18) instanceof PopInstruction);
        assertTrue(instrs.get(19) instanceof PopInstruction);
        assertTrue(instrs.get(20) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_basicOperations() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/BasicOperations.jjc");
        assertEquals(13, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof NewInstruction);
        assertTrue(instrs.get(2) instanceof PushInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        assertTrue(instrs.get(4) instanceof AddInstruction);
        assertTrue(instrs.get(5) instanceof StoreInstruction);
        assertTrue(instrs.get(6) instanceof PushInstruction);
        assertTrue(instrs.get(7) instanceof IncInstruction);
        assertTrue(instrs.get(8) instanceof PushInstruction);
        assertTrue(instrs.get(9) instanceof SwapInstruction);
        assertTrue(instrs.get(10) instanceof PopInstruction);
        assertTrue(instrs.get(11) instanceof PopInstruction);
        assertTrue(instrs.get(12) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_conditionals() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Conditionals.jjc");
        assertEquals(51, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof PushInstruction);
        assertTrue(instrs.get(2) instanceof NewInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        assertTrue(instrs.get(4) instanceof NewInstruction);
        assertTrue(instrs.get(5) instanceof PushInstruction);
        assertTrue(instrs.get(6) instanceof NewInstruction);
        assertTrue(instrs.get(7) instanceof NewInstruction);
        assertTrue(instrs.get(8) instanceof PushInstruction);
        assertTrue(instrs.get(9) instanceof LoadInstruction);
        assertTrue(instrs.get(10) instanceof SupInstruction);
        assertTrue(instrs.get(11) instanceof IfInstruction);
        assertTrue(instrs.get(12) instanceof GotoInstruction);
        assertTrue(instrs.get(13) instanceof PushInstruction);
        assertTrue(instrs.get(14) instanceof IncInstruction);
        assertTrue(instrs.get(15) instanceof LoadInstruction);
        assertTrue(instrs.get(16) instanceof LoadInstruction);
        assertTrue(instrs.get(17) instanceof SupInstruction);
        assertTrue(instrs.get(18) instanceof IfInstruction);
        assertTrue(instrs.get(19) instanceof PushInstruction);
        assertTrue(instrs.get(20) instanceof IncInstruction);
        assertTrue(instrs.get(21) instanceof GotoInstruction);
        assertTrue(instrs.get(22) instanceof PushInstruction);
        assertTrue(instrs.get(23) instanceof StoreInstruction);
        assertTrue(instrs.get(24) instanceof LoadInstruction);
        assertTrue(instrs.get(25) instanceof PushInstruction);
        assertTrue(instrs.get(26) instanceof CmpInstruction);
        assertTrue(instrs.get(27) instanceof IfInstruction);
        assertTrue(instrs.get(28) instanceof LoadInstruction);
        assertTrue(instrs.get(29) instanceof LoadInstruction);
        assertTrue(instrs.get(30) instanceof SupInstruction);
        assertTrue(instrs.get(31) instanceof IfInstruction);
        assertTrue(instrs.get(32) instanceof LoadInstruction);
        assertTrue(instrs.get(33) instanceof StoreInstruction);
        assertTrue(instrs.get(34) instanceof GotoInstruction);
        assertTrue(instrs.get(35) instanceof LoadInstruction);
        assertTrue(instrs.get(36) instanceof StoreInstruction);
        assertTrue(instrs.get(37) instanceof GotoInstruction);
        assertTrue(instrs.get(38) instanceof LoadInstruction);
        assertTrue(instrs.get(39) instanceof StoreInstruction);
        assertTrue(instrs.get(40) instanceof PushInstruction);
        assertTrue(instrs.get(41) instanceof SwapInstruction);
        assertTrue(instrs.get(42) instanceof PopInstruction);
        assertTrue(instrs.get(43) instanceof SwapInstruction);
        assertTrue(instrs.get(44) instanceof PopInstruction);
        assertTrue(instrs.get(45) instanceof SwapInstruction);
        assertTrue(instrs.get(46) instanceof PopInstruction);
        assertTrue(instrs.get(47) instanceof SwapInstruction);
        assertTrue(instrs.get(48) instanceof PopInstruction);
        assertTrue(instrs.get(49) instanceof PopInstruction);
        assertTrue(instrs.get(50) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_method() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Method.jjc");
        assertEquals(30, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof PushInstruction);
        assertTrue(instrs.get(2) instanceof NewInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        assertTrue(instrs.get(4) instanceof NewInstruction);
        assertTrue(instrs.get(5) instanceof PushInstruction);
        assertTrue(instrs.get(6) instanceof NewInstruction);
        assertTrue(instrs.get(7) instanceof GotoInstruction);
        assertTrue(instrs.get(8) instanceof NewInstruction);
        assertTrue(instrs.get(9) instanceof NewInstruction);
        assertTrue(instrs.get(10) instanceof LoadInstruction);
        assertTrue(instrs.get(11) instanceof LoadInstruction);
        assertTrue(instrs.get(12) instanceof AddInstruction);
        assertTrue(instrs.get(13) instanceof SwapInstruction);
        assertTrue(instrs.get(14) instanceof PopInstruction);
        assertTrue(instrs.get(15) instanceof SwapInstruction);
        assertTrue(instrs.get(16) instanceof PopInstruction);
        assertTrue(instrs.get(17) instanceof SwapInstruction);
        assertTrue(instrs.get(18) instanceof ReturnInstruction);
        assertTrue(instrs.get(19) instanceof LoadInstruction);
        assertTrue(instrs.get(20) instanceof LoadInstruction);
        assertTrue(instrs.get(21) instanceof InvokeInstruction);
        assertTrue(instrs.get(22) instanceof StoreInstruction);
        assertTrue(instrs.get(23) instanceof PushInstruction);
        assertTrue(instrs.get(24) instanceof SwapInstruction);
        assertTrue(instrs.get(25) instanceof PopInstruction);
        assertTrue(instrs.get(26) instanceof SwapInstruction);
        assertTrue(instrs.get(27) instanceof PopInstruction);
        assertTrue(instrs.get(28) instanceof PopInstruction);
        assertTrue(instrs.get(29) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_complex() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Complex.jjc");
        assertEquals(49, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof NewInstruction);
        assertTrue(instrs.get(2) instanceof PushInstruction);
        assertTrue(instrs.get(3) instanceof NewInstruction);
        assertTrue(instrs.get(4) instanceof PushInstruction);
        assertTrue(instrs.get(5) instanceof NewInstruction);
        assertTrue(instrs.get(6) instanceof PushInstruction);
        assertTrue(instrs.get(7) instanceof NewInstruction);
        assertTrue(instrs.get(8) instanceof PushInstruction);
        assertTrue(instrs.get(9) instanceof NewInstruction);
        assertTrue(instrs.get(10) instanceof LoadInstruction);
        assertTrue(instrs.get(11) instanceof LoadInstruction);
        assertTrue(instrs.get(12) instanceof LoadInstruction);
        assertTrue(instrs.get(13) instanceof NotInstruction);
        assertTrue(instrs.get(14) instanceof OrInstruction);
        assertTrue(instrs.get(15) instanceof AndInstruction);
        assertTrue(instrs.get(16) instanceof IfInstruction);
        assertTrue(instrs.get(17) instanceof PushInstruction);
        assertTrue(instrs.get(18) instanceof IncInstruction);
        assertTrue(instrs.get(19) instanceof GotoInstruction);
        assertTrue(instrs.get(20) instanceof LoadInstruction);
        assertTrue(instrs.get(21) instanceof LoadInstruction);
        assertTrue(instrs.get(22) instanceof AddInstruction);
        assertTrue(instrs.get(23) instanceof PushInstruction);
        assertTrue(instrs.get(24) instanceof SubInstruction);
        assertTrue(instrs.get(25) instanceof StoreInstruction);
        assertTrue(instrs.get(26) instanceof LoadInstruction);
        assertTrue(instrs.get(27) instanceof LoadInstruction);
        assertTrue(instrs.get(28) instanceof MulInstruction);
        assertTrue(instrs.get(29) instanceof StoreInstruction);
        assertTrue(instrs.get(30) instanceof LoadInstruction);
        assertTrue(instrs.get(31) instanceof LoadInstruction);
        assertTrue(instrs.get(32) instanceof DivInstruction);
        assertTrue(instrs.get(33) instanceof StoreInstruction);
        assertTrue(instrs.get(34) instanceof PushInstruction);
        assertTrue(instrs.get(35) instanceof IncInstruction);
        assertTrue(instrs.get(36) instanceof PushInstruction);
        assertTrue(instrs.get(37) instanceof SwapInstruction);
        assertTrue(instrs.get(38) instanceof PopInstruction);
        assertTrue(instrs.get(39) instanceof SwapInstruction);
        assertTrue(instrs.get(40) instanceof PopInstruction);
        assertTrue(instrs.get(41) instanceof SwapInstruction);
        assertTrue(instrs.get(42) instanceof PopInstruction);
        assertTrue(instrs.get(43) instanceof SwapInstruction);
        assertTrue(instrs.get(44) instanceof PopInstruction);
        assertTrue(instrs.get(45) instanceof SwapInstruction);
        assertTrue(instrs.get(46) instanceof PopInstruction);
        assertTrue(instrs.get(47) instanceof PopInstruction);
        assertTrue(instrs.get(48) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_nop() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Nop.jjc");
        assertEquals(3, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof NopInstruction);
        assertTrue(instrs.get(2) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }

    @Test
    void fromFile_array() throws IOException {
        List<Instruction> instrs = Jajacode.fromFile("src/test/resources/Array.jjc");
        assertEquals(21, instrs.size());

        assertTrue(instrs.get(0) instanceof InitInstruction);
        assertTrue(instrs.get(1) instanceof PushInstruction);
        assertTrue(instrs.get(2) instanceof NewarrayInstruction);
        assertTrue(instrs.get(3) instanceof PushInstruction);
        assertTrue(instrs.get(4) instanceof PushInstruction);
        assertTrue(instrs.get(5) instanceof AstoreInstruction);
        assertTrue(instrs.get(6) instanceof PushInstruction);
        assertTrue(instrs.get(7) instanceof AloadInstruction);
        assertTrue(instrs.get(8) instanceof NegInstruction);
        assertTrue(instrs.get(9) instanceof PushInstruction);
        assertTrue(instrs.get(10) instanceof AstoreInstruction);
        assertTrue(instrs.get(11) instanceof PushInstruction);
        assertTrue(instrs.get(12) instanceof PushInstruction);
        assertTrue(instrs.get(13) instanceof AincInstruction);
        assertTrue(instrs.get(14) instanceof PushInstruction);
        assertTrue(instrs.get(15) instanceof AloadInstruction);
        assertTrue(instrs.get(16) instanceof WriteInstruction);
        assertTrue(instrs.get(17) instanceof LengthInstruction);
        assertTrue(instrs.get(18) instanceof WritelnInstruction);
        assertTrue(instrs.get(19) instanceof PopInstruction);
        assertTrue(instrs.get(20) instanceof JcstopInstruction);

        for (int i = 0; i < instrs.size(); i++) {
            assertEquals(i + 1, instrs.get(i).getLine());
        }
    }






}

