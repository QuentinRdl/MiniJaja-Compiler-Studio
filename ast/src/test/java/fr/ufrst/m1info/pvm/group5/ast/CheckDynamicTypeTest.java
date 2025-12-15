package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.nodes.*;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.SymbolTableEntry;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckDynamicTypeTest {

    Map<String, Value> memoryStorage;
    @Mock
    Memory memoryMock;
    ASTNode opInt;
    ASTNode opBool;
    ASTNode opString;
    ASTNode opVoid;

    @BeforeEach
    void setup() throws Exception {
        memoryStorage = new HashMap<>();
        memoryMock = mock(Memory.class);
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            return memoryStorage.get(ident);
        }).when(memoryMock).val(any(String.class));
        doAnswer(invocation -> {
            String ident = invocation.getArgument(0);
            Value v =memoryStorage.get(ident);
            if (v==null){
                throw new IllegalArgumentException("");
            }
            return switch (v.type) {
                case INT -> DataType.INT;
                case BOOL -> DataType.BOOL;
                case VOID -> DataType.VOID;
                default -> DataType.UNKNOWN;
            };
        }).when(memoryMock).dataTypeOf(any(String.class));
        opInt = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(opInt.checkType(memoryMock)).thenReturn("int");
        opBool = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(opBool.checkType(memoryMock)).thenReturn("bool");
        opString = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(opString.checkType(memoryMock)).thenReturn("string");
        opVoid = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(opVoid.checkType(memoryMock)).thenReturn("void");
    }

    @Test
    @DisplayName("IdentNode - checkType() with int")
    void testIdentNode_Int() throws Exception {
        memoryStorage.put("x", new Value(10));
        IdentNode node = new IdentNode("x");
        assertEquals("int", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("IdentNode - checkType() with bool")
    void testIdentNode_Bool() throws Exception {
        memoryStorage.put("flag", new Value(true));
        IdentNode node = new IdentNode("flag");
        assertEquals("bool", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("IdentNode - checkType() variable not defined")
    void testIdentNode_Undefined() {
        IdentNode node = new IdentNode("y");
        assertThrows(ASTInvalidMemoryException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - int + int (valid)")
    void testAddNode_Int() throws Exception {
        AddNode node = new AddNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("AddNode.checkType - bool + bool (error)")
    void testAddNode_Bool() throws Exception {
        AddNode node = new AddNode(opBool,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - string + string (error)")
    void testAddNode_String() throws Exception {
        AddNode node = new AddNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - void + void (error)")
    void testAddNode_Void() throws Exception {
        AddNode node = new AddNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - int + bool (error)")
    void testAddNode_IntBool() throws Exception {
        AddNode node = new AddNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - bool + int (error)")
    void testAddNode_BoolInt() throws Exception {
        AddNode node = new AddNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - int + string (error)")
    void testAddNode_IntString() throws Exception {
        AddNode node = new AddNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - string + int (error)")
    void testAddNode_StringInt() throws Exception {
        AddNode node = new AddNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () ->node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - int + void (error)")
    void testAddNode_IntVoid() throws Exception {
        AddNode node = new AddNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AddNode.checkType - void + int (error)")
    void testAddNode_VoidInt() throws Exception {
        AddNode node = new AddNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - int = int (valid)")
    void testAffectationNode_IntOk() throws Exception {
        memoryStorage.put("x", new Value(10));
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");
        AffectationNode affectNode = new AffectationNode(new IdentNode("x"), expr);

        String result = affectNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("AffectationNode.checkType - int = bool (error)")
    void testAffectationNode_IncompatibleTypes() throws Exception {
        memoryStorage.put("x", new Value(5));

        ASTNode exprBool = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(exprBool.checkType(memoryMock)).thenReturn("bool");

        AffectationNode node = new AffectationNode(new IdentNode("x"), exprBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - undefined variable")
    void testAffectationNode_UndefinedVariable() throws Exception {
        memoryStorage.put("x", null);

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        AffectationNode node = new AffectationNode(new IdentNode("x"), expr);

        assertThrows(ASTInvalidMemoryException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - bool = bool (valid)")
    void testAffectationNode_BoolOk() throws Exception {
        memoryStorage.put("flag", new Value(true));

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        AffectationNode node = new AffectationNode(new IdentNode("flag"), expr);

        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }
    @Test
    @DisplayName("AffectationNode.checkType - tab[int] = int (valid)")
    void testAffectationNode_ArrayIntIndexIntValue() throws Exception {

        memoryStorage.put("arr", new Value(0));
        when(memoryMock.tabType("arr")).thenReturn(DataType.INT);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("int");
        TabNode tabNode = new TabNode(new IdentNode("arr"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        String result = affectNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[int] = bool (valid with bool array)")
    void testAffectationNode_ArrayIntIndexBoolValue() throws Exception {
        memoryStorage.put("flags", new Value(false));
        when(memoryMock.tabType("flags")).thenReturn(DataType.BOOL);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("bool");
        TabNode tabNode = new TabNode(new IdentNode("flags"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        String result = affectNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[bool] = int (error: index not int)")
    void testAffectationNode_ArrayBoolIndexIntValue() throws Exception {
        memoryStorage.put("arr", new Value(0));
        when(memoryMock.dataTypeOf("arr")).thenReturn(DataType.INT);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("bool");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("int");
        TabNode tabNode = new TabNode(new IdentNode("arr"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> affectNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[int] = bool (error: type mismatch with int array)")
    void testAffectationNode_ArrayIntIndexBoolValueTypeMismatch() throws Exception {
        memoryStorage.put("arr", new Value(0));
        when(memoryMock.dataTypeOf("arr")).thenReturn(DataType.INT);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("bool");
        TabNode tabNode = new TabNode(new IdentNode("arr"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> affectNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[int] = int (error: array not defined)")
    void testAffectationNode_ArrayNotDefined() throws Exception {
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.tabType("undefinedArr")).thenThrow(IllegalArgumentException.class);
        TabNode tabNode = new TabNode(new IdentNode("undefinedArr"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        assertThrows(ASTInvalidMemoryException.class, () -> affectNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[string] = int (error: index not int)")
    void testAffectationNode_ArrayStringIndex() throws Exception {
        memoryStorage.put("arr", new Value(0));
        when(memoryMock.dataTypeOf("arr")).thenReturn(DataType.INT);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("string");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("int");
        TabNode tabNode = new TabNode(new IdentNode("arr"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> affectNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("AffectationNode.checkType - tab[i+1] = value (valid with complex index)")
    void testAffectationNode_ArrayComplexIndexExpression() throws Exception {
        memoryStorage.put("matrix", new Value(100));
        when(memoryMock.tabType("matrix")).thenReturn(DataType.INT);
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(memoryMock)).thenReturn("int");
        TabNode tabNode = new TabNode(new IdentNode("matrix"), indexExpr);
        AffectationNode affectNode = new AffectationNode(tabNode, valueExpr);

        String result = affectNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("AndNode.checkType - int && int (error)")
    void testAndNode_Int() throws Exception {
        AndNode node = new AndNode(opInt,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - bool && bool (valid)")
    void testAndNode_Bool() throws Exception {
        AndNode node = new AndNode(opBool,opBool);

        String result = node.checkType(memoryMock);
        assertEquals("bool", result);
    }

    @Test
    @DisplayName("AndNode.checkType - string && string (error)")
    void testAndNode_String() throws Exception {
        AndNode node = new AndNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - void && void (error)")
    void testAndNode_Void() throws Exception {
        AndNode node = new AndNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - int && bool (error)")
    void testAndNode_IntBool() throws Exception {
        AndNode node = new AndNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - bool && int (error)")
    void testAndNode_BoolInt() throws Exception {
        AndNode node = new AndNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - int && string (error)")
    void testAndNode_IntString() throws Exception {
        AndNode node = new AndNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - string && int (error)")
    void testAndNode_StringInt() throws Exception {
        AndNode node = new AndNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - int && void (error)")
    void testAndNode_IntVoid() throws Exception {
        AndNode node = new AndNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AndNode.checkType - void && int (error)")
    void testAndNode_VoidInt() throws Exception {
        AndNode node = new AndNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - int - int (valid)")
    void testBinMinusNode_Int() throws Exception {
        BinMinusNode node = new BinMinusNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("BinMinusNode.checkType - bool - bool (error)")
    void testBinMinusNode_Bool() throws Exception {
        BinMinusNode node = new BinMinusNode(opBool,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - string - string (error)")
    void testBinMinusNode_String() throws Exception {
        BinMinusNode node = new BinMinusNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - void - void (error)")
    void testBinMinusNode_Void() throws Exception {
        BinMinusNode node = new BinMinusNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - int - bool (error)")
    void testBinMinusNode_IntBool() throws Exception {
        BinMinusNode node = new BinMinusNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - bool - int (error)")
    void testBinMinusNode_BoolInt() throws Exception {
        BinMinusNode node = new BinMinusNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - int - string (error)")
    void testBinMinusNode_IntString() throws Exception {
        BinMinusNode node = new BinMinusNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - string - int (error)")
    void testBinMinusNode_StringInt() throws Exception {
        BinMinusNode node = new BinMinusNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - int - void (error)")
    void testBinMinusNode_IntVoid() throws Exception {
        BinMinusNode node = new BinMinusNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BinMinusNode.checkType - void - int (error)")
    void testBinMinusNode_VoidInt() throws Exception {
        BinMinusNode node = new BinMinusNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("BooleanNode - checkType() return bool")
    void testBooleanNode_CheckType() throws Exception {
        BooleanNode trueNode = new BooleanNode(true);
        BooleanNode falseNode = new BooleanNode(false);

        assertEquals("bool", trueNode.checkType(memoryMock));
        assertEquals("bool", falseNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("ClassNode - checkType() with decls and main")
    void testClassNode_CheckType_WithDeclsAndMain() throws Exception {
        DummyWithdrNode declsNode = new DummyWithdrNode();
        IdentNode classIdent = new IdentNode("C");
        ASTNode mainNode = mock(ASTNode.class);
        when(mainNode.checkType(memoryMock)).thenReturn("void");

        ClassNode classNode = new ClassNode(classIdent, declsNode, mainNode);

        String result = classNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("DeclarationsNode - checkType() with a single statement")
    void testDeclarationsNode_SingleDecl() throws Exception {
        DummyWithdrNode decl = new DummyWithdrNode();

        DeclarationsNode node = new DeclarationsNode(decl, null);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("DeclarationsNode - checkType() with multiple statements")
    void testDeclarationsNode_MultipleDecls() throws Exception {
        DummyWithdrNode decl1 = new DummyWithdrNode();
        DummyWithdrNode decl2 = new DummyWithdrNode();

        DeclarationsNode node = new DeclarationsNode(decl1, decl2);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("DivNode.checkType - int / int (valid)")
    void testDivNode_Int() throws Exception {
        DivNode node = new DivNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("DivNode.checkType - bool / bool (error)")
    void testDivNode_Bool() throws Exception {
        DivNode node = new DivNode(opBool,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - string / string (error)")
    void testDivNode_String() throws Exception {
        DivNode node = new DivNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - void / void (error)")
    void testDivNode_Void() throws Exception {
        DivNode node = new DivNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - int / bool (error)")
    void testDivNode_IntBool() throws Exception {
        DivNode node = new DivNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - bool / int (error)")
    void testDivNode_BoolInt() throws Exception {
        DivNode node = new DivNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - int / string (error)")
    void testDivNode_IntString() throws Exception {
        DivNode node = new DivNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - string / int (error)")
    void testDivNode_StringInt() throws Exception {
        DivNode node = new DivNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - int / void (error)")
    void testDivNode_IntVoid() throws Exception {
        DivNode node = new DivNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("DivNode.checkType - void / int (error)")
    void testDivNode_VoidInt() throws Exception {
        DivNode node = new DivNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - int == int (valid)")
    void testEqualNode_Int() throws Exception {
        EqualNode node = new EqualNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("bool", result);
    }

    @Test
    @DisplayName("EqualNode.checkType - bool == bool (valid)")
    void testEqualNode_Bool() throws Exception {
        EqualNode node = new EqualNode(opBool,opBool);

        String result = node.checkType(memoryMock);
        assertEquals("bool", result);
    }

    @Test
    @DisplayName("EqualNode.checkType - string == string (error)")
    void testEqualNode_String() throws Exception {
        EqualNode node = new EqualNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - void == void (error)")
    void testEqualNode_Void() throws Exception {
        EqualNode node = new EqualNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - int == bool (error)")
    void testEqualNode_IntBool() throws Exception {
        EqualNode node = new EqualNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - bool == int (error)")
    void testEqualNode_BoolInt() throws Exception {
        EqualNode node = new EqualNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - int == string (error)")
    void testEqualNode_IntString() throws Exception {
        EqualNode node = new EqualNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - string == int (error)")
    void testEqualNode_StringInt() throws Exception {
        EqualNode node = new EqualNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - int == void (error)")
    void testEqualNode_IntVoid() throws Exception {
        EqualNode node = new EqualNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("EqualNode.checkType - void == int (error)")
    void testEqualNode_VoidInt() throws Exception {
        EqualNode node = new EqualNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("FinalNode - checkType() valid int")
    void testFinalNode_IntOk() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);
        String result = finalNode.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("FinalNode - checkType() valid bool")
    void testFinalNode_BoolOk() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);
        String result = finalNode.checkType(memoryMock);

        assertEquals("void", result);
    }

    @Test
    @DisplayName("FinalNode - checkType() fails if incompatible type")
    void testFinalNode_TypeMismatch() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");

        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> finalNode.checkType(memoryMock));
    }


    @Test
    @DisplayName("FinalNode - checkType() fails if type not supported")
    void testFinalNode_UnsupportedType() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.VOID);
        IdentNode ident = new IdentNode("x");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("void");

        FinalNode finalNode = new FinalNode(typeNode, ident, expr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> finalNode.checkType(memoryMock));
    }


    @Test
    @DisplayName("IfNode - checkType() valid with condition bool")
    void testIfNode_ValidCondition() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrThen = mock(ASTNode.class);
        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, instrElse);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrThen).checkType(memoryMock);
        verify(instrElse).checkType(memoryMock);
    }

    @Test
    @DisplayName("IfNode - checkType() fails if condition no bool")
    void testIfNode_InvalidCondition() {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("int");

        ASTNode instrThen = mock(ASTNode.class);
        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, instrElse);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> ifNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IfNode - checkType() valid if else null")
    void testIfNode_NullElse() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrThen = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, instrThen, null);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrThen).checkType(memoryMock);
    }

    @Test
    @DisplayName("IfNode - checkType() valid if then null")
    void testIfNode_NullThen() throws Exception {
        ASTNode condition = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(condition.checkType(memoryMock)).thenReturn("bool");

        ASTNode instrElse = mock(ASTNode.class);

        IfNode ifNode = new IfNode(condition, null, instrElse);
        String result = ifNode.checkType(memoryMock);

        assertEquals("void", result);
        verify(instrElse).checkType(memoryMock);
    }

    @Test
    @DisplayName("IncNode - checkType() valid with int")
    void testIncNode_IntOk() throws Exception {
        memoryStorage.put("x", new Value(5));

        IdentNode identNode = new IdentNode("x");
        IncNode incNode = new IncNode(identNode);

        assertEquals("int", incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails if variable not defined")
    void testIncNode_UndefinedVariable() {
        IdentNode identNode = new IdentNode("y");
        IncNode incNode = new IncNode(identNode);

        assertThrows(ASTInvalidMemoryException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails if variable not int")
    void testIncNode_NonIntVariable() {
        memoryStorage.put("flag", new Value(true));

        IdentNode identNode = new IdentNode("flag");
        IncNode incNode = new IncNode(identNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }
    @Test
    @DisplayName("IncNode - checkType() valid with int array and int index")
    void testIncNode_IntArrayIntIndex() throws Exception {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertEquals("int", incNode.checkType(memoryMock));
        verify(indexExpr).checkType(memoryMock);
        verify(memoryMock).contains("arr");
        verify(memoryMock).tabLength("arr");
    }

    @Test
    @DisplayName("IncNode - checkType() fails with undeclared array")
    void testIncNode_UndeclaredArray() {
        IdentNode arrayIdent = new IdentNode("undeclared");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("undeclared")).thenReturn(false);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails when identifier is not an array")
    void testIncNode_NotAnArray() {
        memoryStorage.put("x", new Value(5));
        IdentNode arrayIdent = new IdentNode("x");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("x")).thenReturn(true);
        when(memoryMock.tabLength("x")).thenReturn(-1);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails with non-int array index")
    void testIncNode_NonIntArrayIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("bool");
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() fails with string array index")
    void testIncNode_StringArrayIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("string");
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> incNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("IncNode - checkType() valid with complex index expression")
    void testIncNode_ComplexIndexExpression() throws Exception {
        IdentNode arrayIdent = new IdentNode("matrix");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("matrix")).thenReturn(true);
        when(memoryMock.tabLength("matrix")).thenReturn(100);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        IncNode incNode = new IncNode(tabNode);

        assertEquals("int", incNode.checkType(memoryMock));
        verify(indexExpr).checkType(memoryMock);
        verify(memoryMock).contains("matrix");
        verify(memoryMock).tabLength("matrix");
    }

    @Test
    @DisplayName("InstructionsNode - checkType() with instruction only")
    void testInstructionsNode_SingleInstruction() throws Exception {
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenReturn("void");

        InstructionsNode node = new InstructionsNode(instr, null);

        assertEquals("void", node.checkType(memoryMock));
        verify(instr, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("InstructionsNode - checkType() with several instructions")
    void testInstructionsNode_MultipleInstructions() throws Exception {
        ASTNode instr1 = mock(ASTNode.class);
        ASTNode instr2 = mock(ASTNode.class);

        when(instr1.checkType(memoryMock)).thenReturn("void");
        when(instr2.checkType(memoryMock)).thenReturn("void");

        InstructionsNode node = new InstructionsNode(instr1, instr2);

        assertEquals("void", node.checkType(memoryMock));
        verify(instr1, times(1)).checkType(memoryMock);
        verify(instr2, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("InstructionsNode - checkType() fails if statement fails")
    void testInstructionsNode_FailingInstruction() throws Exception {
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        InstructionsNode node = new InstructionsNode(instr, null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MainNode - checkType() with vars And instrs")
    void testMainNode_WithVarsAndInstrs() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        ASTNode instrs = mock(ASTNode.class);

        when(vars.checkType(memoryMock)).thenReturn("void");
        when(instrs.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(vars, instrs);

        assertEquals("void", node.checkType(memoryMock));
        verify(vars, times(1)).checkType(memoryMock);
        verify(instrs, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() With vars null")
    void testMainNode_VarsNull() throws Exception {
        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(null, instrs);

        assertEquals("void", node.checkType(memoryMock));
        verify(instrs, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() With instrs null")
    void testMainNode_InstrsNull() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        when(vars.checkType(memoryMock)).thenReturn("void");

        MainNode node = new MainNode(vars, null);

        assertEquals("void", node.checkType(memoryMock));
        verify(vars, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("MainNode - checkType() fails if vars fails")
    void testMainNode_VarsFail() throws Exception {
        ASTNode vars = mock(ASTNode.class, withSettings().extraInterfaces(WithdrawalNode.class));
        when(vars.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        MainNode node = new MainNode(vars, null);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MainNode - checkType() fails if instrs fails")
    void testMainNode_InstrsFail() throws Exception {
        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("Error"));

        MainNode node = new MainNode(null, instrs);

        assertThrows(ASTInvalidDynamicTypeException.class, ()->node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - int * int (valid)")
    void testMulNode_Int() throws Exception {
        MulNode node = new MulNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("MulNode.checkType - bool * bool (error)")
    void testMulNode_Bool() throws Exception {
        MulNode node = new MulNode(opBool,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - string * string (error)")
    void testMulNode_String() throws Exception {
        MulNode node = new MulNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - void * void (error)")
    void testMulNode_Void() throws Exception {
        MulNode node = new MulNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - int * bool (error)")
    void testMulNode_IntBool() throws Exception {
        MulNode node = new MulNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - bool * int (error)")
    void testMulNode_BoolInt() throws Exception {
        MulNode node = new MulNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - int * string (error)")
    void testMulNode_IntString() throws Exception {
        MulNode node = new MulNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - string * int (error)")
    void testMulNode_StringInt() throws Exception {
        MulNode node = new MulNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - int * void (error)")
    void testMulNode_IntVoid() throws Exception {
        MulNode node = new MulNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("MulNode.checkType - void * int (error)")
    void testMulNode_VoidInt() throws Exception {
        MulNode node = new MulNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("NotNode - checkType() valid with bool")
    void testNotNode_Bool() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        NotNode node = new NotNode(expr);

        assertEquals("bool", node.checkType(memoryMock));
        verify(expr, times(1)).checkType(memoryMock);
    }

    @Test
    @DisplayName("NotNode - checkType() fails if non-bool")
    void testNotNode_NonBool() {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        NotNode node = new NotNode(expr);

        ASTInvalidDynamicTypeException exception = assertThrows(
                ASTInvalidDynamicTypeException.class,
                () -> node.checkType(memoryMock)
        );
        assertTrue(exception.getMessage().contains("non-bool"));
    }

    @Test
    @DisplayName("NotNode - checkType() fails if expr is null in constructor")
    void testNotNode_NullExpr() {
        assertThrows(ASTBuildException.class, () -> new NotNode(null));
    }

    @Test
    @DisplayName("NumberNode - checkType() return int")
    void testNumberNode_CheckType() throws Exception {
        NumberNode node = new NumberNode(42);
        assertEquals("int", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("NumberNode - eval() returns the correct value")
    void testNumberNode_Eval() throws Exception {
        NumberNode node = new NumberNode(99);
        Value val = node.eval(new Memory());
        assertEquals(99, val.valueInt);
    }

    @Test
    @DisplayName("OrNode.checkType - int || int (error)")
    void testOrNode_Int() throws Exception {
        OrNode node = new OrNode(opInt,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - bool || bool (valid)")
    void testOrNode_Bool() throws Exception {
        OrNode node = new OrNode(opBool,opBool);

        String result = node.checkType(memoryMock);
        assertEquals("bool", result);
    }

    @Test
    @DisplayName("OrNode.checkType - string || string (error)")
    void testOrNode_String() throws Exception {
        OrNode node = new OrNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - void || void (error)")
    void testOrNode_Void() throws Exception {
        OrNode node = new OrNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - int || bool (error)")
    void testOrNode_IntBool() throws Exception {
        OrNode node = new OrNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - bool || int (error)")
    void testOrNode_BoolInt() throws Exception {
        OrNode node = new OrNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - int || string (error)")
    void testOrNode_IntString() throws Exception {
        OrNode node = new OrNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - string || int (error)")
    void testOrNode_StringInt() throws Exception {
        OrNode node = new OrNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - int || void (error)")
    void testOrNode_IntVoid() throws Exception {
        OrNode node = new OrNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("OrNode.checkType - void || int (error)")
    void testOrNode_VoidInt() throws Exception {
        OrNode node = new OrNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("ReturnNode - checkType() returns type of expression int")
    void testReturnNode_CheckType_Int() throws Exception {
        NumberNode numberExpr = new NumberNode(10);
        ReturnNode returnNode = new ReturnNode(numberExpr);

        assertEquals("int", returnNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("ReturnNode - checkType() returns type of bool expression")
    void testReturnNode_CheckType_Bool() throws Exception {
        BooleanNode boolExpr = new BooleanNode(true);
        ReturnNode returnNode = new ReturnNode(boolExpr);

        assertEquals("bool", returnNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() valid with int")
    void testSumNode_IntOk() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");
        Memory memoryMock = mock(Memory.class);
        when(memoryMock.dataTypeOf("x")).thenReturn(DataType.INT);


        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        String result = sumNode.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("SumNode - checkType() fails if expression is not int")
    void testSumNode_ExprNonInt() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("bool");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.dataTypeOf("x")).thenReturn(DataType.INT);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails if variable not defined")
    void testSumNode_UndefinedVariable() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.dataTypeOf("x")).thenThrow(IllegalArgumentException.class);

        assertThrows(ASTInvalidMemoryException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails if variable not int")
    void testSumNode_VariableNonInt() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(any(Memory.class))).thenReturn("int");

        SumNode sumNode = new SumNode(new IdentNode("x"), expr);

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.dataTypeOf("x")).thenReturn(DataType.BOOL);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }
    @Test
    @DisplayName("SumNode - checkType() valid with int array and int index")
    void testSumNode_IntArrayIntIndex() throws Exception {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("int");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("int");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertEquals("int", sumNode.checkType(memoryMock));
        verify(indexExpr).checkType(memoryMock);
        verify(valueExpr).checkType(memoryMock);
        verify(memoryMock).contains("arr");
        verify(memoryMock).tabLength("arr");
    }

    @Test
    @DisplayName("SumNode - checkType() fails with undeclared array")
    void testSumNode_UndeclaredArray() {
        IdentNode arrayIdent = new IdentNode("undeclared");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("int");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("int");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("undeclared")).thenReturn(false);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails when identifier is not an array")
    void testSumNode_NotAnArray() {
        IdentNode arrayIdent = new IdentNode("x");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("int");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("int");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("x")).thenReturn(true);
        when(memoryMock.tabLength("x")).thenReturn(-1);
        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails with non-int array index")
    void testSumNode_NonIntArrayIndex() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("bool");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("int");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() fails with non-int value expression")
    void testSumNode_NonIntValueExpression() {
        IdentNode arrayIdent = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("int");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("bool");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.tabLength("arr")).thenReturn(10);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> sumNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("SumNode - checkType() valid with complex index expression")
    void testSumNode_ComplexIndexExpression() throws Exception {
        IdentNode arrayIdent = new IdentNode("matrix");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(any(Memory.class))).thenReturn("int");

        ASTNode valueExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(valueExpr.checkType(any(Memory.class))).thenReturn("int");

        Memory memoryMock = mock(Memory.class);
        when(memoryMock.contains("matrix")).thenReturn(true);
        when(memoryMock.tabLength("matrix")).thenReturn(100);

        TabNode tabNode = new TabNode(arrayIdent, indexExpr);
        SumNode sumNode = new SumNode(tabNode, valueExpr);

        assertEquals("int", sumNode.checkType(memoryMock));
        verify(indexExpr).checkType(memoryMock);
        verify(valueExpr).checkType(memoryMock);
        verify(memoryMock).contains("matrix");
        verify(memoryMock).tabLength("matrix");
    }

    @Test
    @DisplayName("SupNode.checkType - int > int (valid)")
    void testSupNode_Int() throws Exception {
        SupNode node = new SupNode(opInt,opInt);

        String result = node.checkType(memoryMock);
        assertEquals("bool", result);
    }

    @Test
    @DisplayName("SupNode.checkType - bool > bool (error)")
    void testSupNode_Bool() throws Exception {
        SupNode node = new SupNode(opBool,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));

    }

    @Test
    @DisplayName("SupNode.checkType - string > string (error)")
    void testSupNode_String() throws Exception {
        SupNode node = new SupNode(opString,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - void > void (error)")
    void testSupNode_Void() throws Exception {
        SupNode node = new SupNode(opVoid,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - int > bool (error)")
    void testSupNode_IntBool() throws Exception {
        SupNode node = new SupNode(opInt,opBool);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - bool > int (error)")
    void testSupNode_BoolInt() throws Exception {
        SupNode node = new SupNode(opBool,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - int > string (error)")
    void testSupNode_IntString() throws Exception {
        SupNode node = new SupNode(opInt,opString);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - string > int (error)")
    void testSupNode_StringInt() throws Exception {
        SupNode node = new SupNode(opString,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - int > void (error)")
    void testSupNode_IntVoid() throws Exception {
        SupNode node = new SupNode(opInt,opVoid);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("SupNode.checkType - void > int (error)")
    void testSupNode_VoidInt() throws Exception {
        SupNode node = new SupNode(opVoid,opInt);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("UnMinusNode - checkType() valid with int")
    void testUnMinusNode_IntOk() throws Exception {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        UnMinusNode node = new UnMinusNode(expr);
        String result = node.checkType(memoryMock);
        assertEquals("int", result);
    }

    @Test
    @DisplayName("UnMinusNode - checkType() fails if expression not int")
    void testUnMinusNode_NonInt() {
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("bool");

        UnMinusNode node = new UnMinusNode(expr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("VariableNode - checkType() valid with compatible expression")
    void testVariableNode_CheckType_Valid() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        VariableNode node = new VariableNode(typeNode, ident, expr);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariableNode - checkType() fails if incompatible type")
    void testVariableNode_CheckType_Incompatible() {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");
        ASTNode expr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(expr.checkType(memoryMock)).thenReturn("int");

        VariableNode node = new VariableNode(typeNode, ident, expr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("VariableNode - checkType() valid if no expression")
    void testVariableNode_CheckType_NoExpression() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");

        VariableNode node = new VariableNode(typeNode, ident, null);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() valid with a variable")
    void testVariablesNode_CheckType_Single() throws Exception {
        ASTNode var = new DummyWithdrNode();

        VariablesNode node = new VariablesNode(var, null);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() valid with multiple variables")
    void testVariablesNode_CheckType_Multiple() throws Exception {
        ASTNode var1 = new DummyWithdrNode();
        ASTNode var2 = new DummyWithdrNode();

        VariablesNode node = new VariablesNode(var1, var2);
        String result = node.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("VariablesNode - checkType() fails if incompatible variable")
    void testVariablesNode_CheckType_Incompatible() {
        ASTNode notWithdrawable = mock(ASTNode.class);

        assertThrows(ASTBuildException.class, () -> new VariablesNode(notWithdrawable, null));
    }

    @Test
    @DisplayName("WhileNode - checkType() with bool condition and instructions")
    void testWhileNode_CheckType_Valid() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("bool");
        ASTNode instr = mock(ASTNode.class);
        when(instr.checkType(memoryMock)).thenReturn("void");

        WhileNode whileNode = new WhileNode(cond, instr);
        String result = whileNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("WhileNode - checkType() with non-bool condition")
    void testWhileNode_CheckType_InvalidCondition() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("int");

        ASTNode instr = mock(ASTNode.class);

        WhileNode whileNode = new WhileNode(cond, instr);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> whileNode.checkType(memoryMock));
    }

    @Test
    @DisplayName("WhileNode - checkType() without instructions")
    void testWhileNode_CheckType_NoInstructions() throws Exception {
        ASTNode cond = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(cond.checkType(memoryMock)).thenReturn("bool");

        WhileNode whileNode = new WhileNode(cond, null);
        String result = whileNode.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("ParamNode - checkType() INT")
    void testParamNode_CheckType_Int() throws Exception {
        TypeNode type = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("x");
        ParamNode node = new ParamNode(type, ident);

        assertEquals("void", node.checkType(memoryMock));
        verify(memoryMock).declVar(eq("x"), any(Value.class), eq(DataType.INT));
    }

    @Test
    @DisplayName("ParamNode - checkType() BOOL")
    void testParamNode_CheckType_Bool() throws Exception {
        TypeNode type = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flag");
        ParamNode node = new ParamNode(type, ident);

        assertEquals("void", node.checkType(memoryMock));
        verify(memoryMock).declVar(eq("flag"), any(Value.class), eq(DataType.BOOL));
    }

    @Test
    @DisplayName("ParamNode - checkType() VOID → erreur")
    void testParamNode_CheckType_Void() {
        TypeNode type = new TypeNode(ValueType.VOID);
        IdentNode ident = new IdentNode("v");
        ParamNode node = new ParamNode(type, ident);

        assertThrows(ASTInvalidDynamicTypeException.class,
                () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("ParamListNode - checkType() valid parameters")
    void testParamListNode_CheckType_Valid() throws Exception {
        ParamNode p1 = new ParamNode(new TypeNode(ValueType.INT), new IdentNode("x"));
        ParamNode p2 = new ParamNode(new TypeNode(ValueType.BOOL), new IdentNode("flag"));
        ParamListNode list = new ParamListNode(p1, new ParamListNode(p2, null));
        String result = list.checkType(memoryMock);
        assertEquals("void", result);
    }

    @Test
    @DisplayName("ParamListNode - checkType() void type rejected")
    void testParamListNode_CheckType_Void_Throws() {
        ParamNode invalid = new ParamNode(new TypeNode(ValueType.VOID), new IdentNode("v"));

        ParamListNode list = new ParamListNode(invalid, null);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> list.checkType(memoryMock));

        verifyNoInteractions(memoryMock);
    }
    @Test
    @DisplayName("ExpListNode - checkType() simple two expressions")
    void testExpListNode_CheckType_Two() throws Exception {
        ExpListNode list = new ExpListNode(opInt, new ExpListNode(opBool, null));

        String type = list.checkType(memoryMock);

        assertEquals("void", type);
        verify(opInt).checkType(memoryMock);
        verify(opBool).checkType(memoryMock);
    }

    @Test
    @DisplayName("ExpListNode - checkType() throws if head throws")
    void testExpListNode_CheckType_HeadThrows() throws Exception {
        ASTNode bad = mock(ASTNode.class);
        when(bad.checkType(memoryMock)).thenThrow(new ASTInvalidDynamicTypeException("err"));

        ExpListNode list = new ExpListNode(bad, null);

        assertThrows(ASTInvalidDynamicTypeException.class,
                () -> list.checkType(memoryMock));
    }
    @Test
    @DisplayName("ExpListNode - checkType() single element")
    void testExpListNodeCheckTypeSingle() throws Exception {
        ExpListNode list = new ExpListNode(opInt, null);

        String type = list.checkType(memoryMock);
        assertEquals("void", type);
        verify(opInt).checkType(memoryMock);
    }
    @Test
    void testMethodeNodeCheckType() {
        Memory memory = mock(Memory.class);

        TypeNode returnType = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("foo");
        ASTNode params = ASTMocks.createNode(ASTNode.class, null, i -> List.of());

        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(memory)).thenReturn("int");

        MethodeNode method = new MethodeNode(returnType, ident, params, null, instrs);

        String result = method.checkType(memory);

        assertEquals("int", result);
        verify(instrs).checkType(memory);
    }
    @Test
    void testMethodeNodeCheckType_NoInstrs() {
        Memory memory = mock(Memory.class);

        MethodeNode method = new MethodeNode(
                new TypeNode(ValueType.BOOL),
                new IdentNode("test"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                null
        );

        assertThrows(ASTInvalidDynamicTypeException.class, () -> method.checkType(memory));
    }
    @Test
    void testMethodeNodeCheckType_Int() {
        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.INT),
                new IdentNode("f"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                new InstructionsNode(new ReturnNode(new NumberNode(3)),null)
        );
        assertEquals("int", m.checkType(mock(Memory.class)));
    }

    @Test
    void testMethodeNodeCheckType_Bool() {
        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.BOOL),
                new IdentNode("f"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                new InstructionsNode(new ReturnNode(new BooleanNode(false)),null)
        );
        assertEquals("bool", m.checkType(mock(Memory.class)));
    }

    @Test
    void testMethodeNodeCheckType_Void() {
        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.VOID),
                new IdentNode("f"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                null
        );
        assertEquals("void", m.checkType(mock(Memory.class)));
    }
    @Test
    void testMethodeNodeCheckType_WithInstrs() {
        Memory mem = mock(Memory.class);

        ASTNode instrs = mock(ASTNode.class);
        when(instrs.checkType(mem)).thenReturn("int");

        MethodeNode m = new MethodeNode(
                new TypeNode(ValueType.INT),
                new IdentNode("f"),
                ASTMocks.createNode(ASTNode.class, null, i -> List.of()),
                null,
                instrs
        );

        String result = m.checkType(mem);

        assertEquals("int", result);
        verify(instrs).checkType(mem);
    }
    @Test
    @DisplayName("AppelENode.checkType - method returns int (valid)")
     void testAppelENode_CheckType_Int() throws Exception {
        IdentNode ident = new IdentNode("myFunc");
        SymbolTableEntry methodEntry = mock(SymbolTableEntry.class);
        when(methodEntry.getKind()).thenReturn(EntryKind.METHOD);
        when(methodEntry.getDataType()).thenReturn(DataType.INT);
        when(memoryMock.getMethod("myFunc")).thenReturn(methodEntry);

        AppelENode node = new AppelENode(ident, null);
        assertEquals("int", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AppelENode.checkType - method returns bool (valid)")
     void testAppelENode_CheckType_Bool() throws Exception {
        IdentNode ident = new IdentNode("myFunc");
        SymbolTableEntry methodEntry = mock(SymbolTableEntry.class);
        when(methodEntry.getKind()).thenReturn(EntryKind.METHOD);
        when(methodEntry.getDataType()).thenReturn(DataType.BOOL);
        when(memoryMock.getMethod("myFunc")).thenReturn(methodEntry);

        AppelENode node = new AppelENode(ident, null);
        assertEquals("bool", node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AppelENode.checkType - method returns void (error)")
     void testAppelENode_CheckType_Void() {
        IdentNode ident = new IdentNode("myProc");
        SymbolTableEntry methodEntry = mock(SymbolTableEntry.class);
        when(methodEntry.getKind()).thenReturn(EntryKind.METHOD);
        when(methodEntry.getDataType()).thenReturn(DataType.VOID);
        when(memoryMock.getMethod("myProc")).thenReturn(methodEntry);

        AppelENode node = new AppelENode(ident, null);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AppelENode.checkType - identifier is not a method (error)")
     void testAppelENode_CheckType_NotAMethod() {
        IdentNode ident = new IdentNode("myVar");
        SymbolTableEntry varEntry = mock(SymbolTableEntry.class);
        when(varEntry.getKind()).thenReturn(EntryKind.VARIABLE);
        when(memoryMock.getMethod("myVar")).thenReturn(varEntry);

        AppelENode node = new AppelENode(ident, null);
        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("AppelENode.checkType - method not found (error)")
     void testAppelENode_CheckType_NotFound() {
        IdentNode ident = new IdentNode("ghostFunc");
        when(memoryMock.getMethod("ghostFunc")).thenThrow(new Memory.MemoryIllegalArgException("Unknown method ghostFunc"));

        AppelENode node = new AppelENode(ident, null);
        assertThrows(Memory.MemoryIllegalArgException.class, () -> node.checkType(memoryMock));
    }
    @Test
    @DisplayName("ArrayNode - checkType() valid with int size expression")
    void testArrayNode_CheckType_ValidIntSize() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        ASTNode sizeExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(sizeExpr.checkType(memoryMock)).thenReturn("int");

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
        verify(memoryMock).declTab(eq("arr"), eq(1), eq(DataType.INT));
    }

    @Test
    @DisplayName("ArrayNode - checkType() fails with non-int size expression")
    void testArrayNode_CheckType_InvalidSizeType() {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("arr");
        ASTNode sizeExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(sizeExpr.checkType(memoryMock)).thenReturn("bool");

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("ArrayNode - checkType() declares temporary array in memory")
    void testArrayNode_CheckType_DeclaresTemporaryArray() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.BOOL);
        IdentNode ident = new IdentNode("flags");
        ASTNode sizeExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(sizeExpr.checkType(memoryMock)).thenReturn("int");

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
        verify(memoryMock).declTab(eq("flags"), eq(1), eq(DataType.BOOL));
    }

    @Test
    @DisplayName("ArrayNode - checkType() with complex size expression")
    void testArrayNode_CheckType_ComplexSizeExpression() throws Exception {
        TypeNode typeNode = new TypeNode(ValueType.INT);
        IdentNode ident = new IdentNode("matrix");
        ASTNode sizeExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(sizeExpr.checkType(memoryMock)).thenReturn("int");

        ArrayNode node = new ArrayNode(typeNode, ident, sizeExpr);
        String result = node.checkType(memoryMock);

        assertEquals("void", result);
        verify(sizeExpr).checkType(memoryMock);
        verify(memoryMock).declTab(eq("matrix"), eq(1), eq(DataType.INT));
    }

    @Test
    @DisplayName("TabNode - checkType() valid with int array and int index")
    void testTabNode_CheckType_ValidIntArrayIntIndex() throws Exception {
        IdentNode ident = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("arr")).thenReturn(true);
        when(memoryMock.valueTypeOf("arr")).thenReturn(ValueType.INT);
        doCallRealMethod().when(memoryMock).dataTypeOf("arr");

        TabNode node = new TabNode(ident, indexExpr);
        String result = node.checkType(memoryMock);

        assertEquals("int", result);
        verify(indexExpr).checkType(memoryMock);
        verify(memoryMock).contains("arr");
        verify(memoryMock).valueTypeOf("arr");
    }



    @Test
    @DisplayName("TabNode - checkType() valid with bool array and int index")
    void testTabNode_CheckType_ValidBoolArrayIntIndex() throws Exception {
        memoryStorage.put("flags", new Value(false));

        IdentNode ident = new IdentNode("flags");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("flags")).thenReturn(true);
        when(memoryMock.dataTypeOf("flags")).thenReturn(DataType.BOOL);

        TabNode node = new TabNode(ident, indexExpr);
        String result = node.checkType(memoryMock);

        assertEquals("bool", result);
        verify(indexExpr).checkType(memoryMock);
        verify(memoryMock).contains("flags");
        verify(memoryMock).dataTypeOf("flags");
    }

    @Test
    @DisplayName("TabNode - checkType() fails when identifier not declared")
    void testTabNode_CheckType_IdentifierNotDeclared() {
        IdentNode ident = new IdentNode("undeclared");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("undeclared")).thenReturn(false);

        TabNode node = new TabNode(ident, indexExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("TabNode - checkType() fails with non-int index")
    void testTabNode_CheckType_NonIntIndex() {
        IdentNode ident = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("bool");
        when(memoryMock.contains("arr")).thenReturn(true);

        TabNode node = new TabNode(ident, indexExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("TabNode - checkType() fails with string index")
    void testTabNode_CheckType_StringIndex() {
        IdentNode ident = new IdentNode("arr");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("string");
        when(memoryMock.contains("arr")).thenReturn(true);

        TabNode node = new TabNode(ident, indexExpr);

        assertThrows(ASTInvalidDynamicTypeException.class, () -> node.checkType(memoryMock));
    }

    @Test
    @DisplayName("TabNode - checkType() with complex index expression")
    void testTabNode_CheckType_ComplexIndexExpression() throws Exception {
        memoryStorage.put("matrix", new Value(100));

        IdentNode ident = new IdentNode("matrix");
        ASTNode indexExpr = mock(ASTNode.class, withSettings().extraInterfaces(EvaluableNode.class));
        when(indexExpr.checkType(memoryMock)).thenReturn("int");
        when(memoryMock.contains("matrix")).thenReturn(true);
        when(memoryMock.dataTypeOf("matrix")).thenReturn(DataType.INT);

        TabNode node = new TabNode(ident, indexExpr);
        String result = node.checkType(memoryMock);

        assertEquals("int", result);
        verify(indexExpr).checkType(memoryMock);
        verify(memoryMock).contains("matrix");
        verify(memoryMock).dataTypeOf("matrix");
    }


    class DummyWithdrNode extends ASTNode implements WithdrawalNode {
        @Override
        public List<String> compile(int address) { return List.of(); }
        @Override
        public void interpret(Memory m) { }
        @Override
        public void withdrawInterpret(Memory m) { }
        @Override
        public List<String> withdrawCompile(int address) { return List.of(); }
        @Override
        public String checkType(Memory m) { return "void"; }
        @Override
        public List<ASTNode> getChildren() {
            return List.of();
        }
    }
}
