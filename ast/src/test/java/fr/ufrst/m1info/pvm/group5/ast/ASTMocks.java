package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.Nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import fr.ufrst.m1info.pvm.group5.memory.Stack.StackIsEmptyException;
import fr.ufrst.m1info.pvm.group5.memory.Memory.MemoryIllegalArgException;


/**
 * Static class to quickly create classes mocking Memory
 */
public class ASTMocks {

    public record Pair<T,V>(T first, V second){}

    /**
     * Creates a mock with no specific behaviour
     * @return mock created
     */
    public static Memory createEmptyMemory(){
        return mock(Memory.class);
    }

    /**
     * Creates a mock that mimics memory by using a map
     * @param storage map used to store the mock data
     * @return mock created
     */
    public static Memory createMemoryWithStorage(Map<String, Value> storage){
        Memory result = createEmptyMemory();
        doAnswer(invocation -> {
                    String arg =  invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    if(!storage.containsKey(arg)){
                        throw new ASTInvalidMemoryException("Unknown variable "+arg);
                    }
                    storage.put(arg, value);
                    return null;
                }
        ).when(result).affectValue(any(String.class), any(Object.class));

        doAnswer( invocation -> {
                    String ident = invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    storage.put(ident, value);
                    return null;
                }
        ).when(result).declVar(any(String.class), any(Value.class), any());

        doAnswer( invocation -> {
                    String ident = invocation.getArgument(0);
                    Value value = invocation.getArgument(1);
                    storage.put(ident, value);
                    return null;
                }
        ).when(result).declCst(any(String.class), any(Value.class), any());

        doAnswer( invocation -> {
                    String ident = invocation.getArgument(0);
                    storage.put(ident, new Value());
                    return null;
                }
        ).when(result).declVarClass(any(String.class));

        doAnswer(invocation -> {
                    String identifier =  invocation.getArgument(0);
                    return storage.get(identifier);
                }
        ).when(result).val(any(String.class));
        return result;
    }

    /**
     * Add a class variable identifier to a memory mock
     * The class variable is expected to be already declared in the mock, as this function doesn't do any declaration
     *
     * @param mock MemoryMock, that must be a mock of Memory created earlier
     * @param classVariableIdent identifier of the class variable.
     * @return modified mock
     */
    public static Memory addClassVariableToMock(Memory mock, String classVariableIdent){
        doAnswer(invocationOnMock -> {
            return classVariableIdent;
        }).when(mock).identVarClass();
        return mock;
    }

    /**
     * Add primitives related to the writer to a memory mock
     *
     * @param mock mock of the memory class created earlier
     * @param writerReference Reference to store the content of the writer calls
     * @return modified mock
     */
    public static Memory addWriterToMock(Memory mock, List<String> writerReference){
        doAnswer(invocationOnMock -> {
            writerReference.add(invocationOnMock.getArgument(0));
            return null;
        }).when(mock).write(any(String.class));

        doAnswer(invocationOnMock -> {
            writerReference.add(invocationOnMock.getArgument(0) + "\n");
            return null;
        }).when(mock).writeLine(any(String.class));
        return mock;
    }

    /**
     * Creates a mock that mimics memory by using a stack, with the purpose to test jajacode instructions
     * @param storage stack used to store the mock's data
     * @return mock created
     */
    public static Memory createMemoryWithStack(Stack<Pair<String,Value>> storage){
        Memory result = createEmptyMemory();
        doAnswer(invocationOnMock -> {
            Pair<String,Value> pair  = new Pair<>(
                    invocationOnMock.getArgument(0),
                    invocationOnMock.getArgument(1)
            );
            storage.push(pair);
            return null;
        }).when(result).declVar(any(String.class), any(Value.class), any());

        doAnswer(invocationOnMock -> {
            Pair<String,Value> pair  = new Pair<>(
                    invocationOnMock.getArgument(0),
                    invocationOnMock.getArgument(1)
            );
            storage.push(pair);
            return null;
        }).when(result).declCst(any(String.class), any(Value.class), any());

        doAnswer(invocationOnMock -> {
            Pair<String,Value> pair  = new Pair<>(
                    invocationOnMock.getArgument(0),
                    invocationOnMock.getArgument(1)
            );
            storage.push(pair);
            return null;
        }).when(result).push(any(String.class), any(Value.class), any(), any());

        doAnswer(invocationOnMock -> {
            Stack<Pair<String,Value>> stack = new Stack<>();
            boolean found = false;
            while(!found && !storage.empty()){
                Pair<String,Value> pair = storage.pop();
                if(pair.first.equals(invocationOnMock.getArgument(0))){
                    found = true;
                    storage.push(new Pair<>(invocationOnMock.getArgument(0), invocationOnMock.getArgument(1)));
                }
                else{
                    stack.push(pair);
                }
            }
            for(Pair<String,Value> pair : stack)
                storage.push(pair);
            return null;
        }).when(result).affectValue(any(String.class), any(Value.class));


        doAnswer(invocationOnMock -> {
            if(storage.empty()){
                throw new StackIsEmptyException("The stack is empty, cannot pop");
            }
            return storage.pop().second();
        }).when(result).pop();


        /*
        try {
            doAnswer(invocationOnMock -> {
                try {
                    return storage.pop().second;
                } catch (Exception e) {
                    return null;
                }
            }).when(result).pop();
        }catch (Exception _){}
         */


        doAnswer(invocationOnMock -> {
            Value v = null;
            Stack<Pair<String,Value>> stack = new Stack<>();
            while(v == null && !storage.empty()){
                Pair<String,Value> pair = storage.pop();
                if(pair.first.equals(invocationOnMock.getArgument(0))){
                    v = pair.second;
                    storage.push(new Pair<>(invocationOnMock.getArgument(0), v));
                }
                stack.push(pair);
            }
            for(Pair<String,Value> pair : stack)
                storage.push(pair);
            return v;
        }).when(result).val(any(String.class));

        doAnswer(invocationOnMock -> {
            if(storage.size() < 2){
                throw new MemoryIllegalArgException("Not enough elements to swap");
            }
            var v1 = storage.pop();
            var v2 = storage.pop();
            storage.push(v1);
            storage.push(v2);
            return null;
        }).when(result).swap();

        return result;
    }

    /**
     * Creates a mock that mimics memory storage and withdrawal using a map
     * @param storage map using to store the mock data
     * @return mock created
     */
    public static Memory createMemoryWithWithdraw(Map<String, Value> storage){
        Memory result = createMemoryWithStorage(storage);
        doAnswer(invocation -> {
            storage.remove((String)invocation.getArgument(0));
            return null;
        }).when(result).withdrawDecl(any(String.class));
        return result;
    }

    /**
     * Creates a mock to mimic a node of the AST
     * @param type class of node to create
     * @param onInterpret function called when the "interpret" method is called from the mock
     * @param onCompile function called when the "compile" method is called from the mock
     * @return mock created
     * @param <T> class of node to create, defined by the [type] parameter
     */
    public static<T extends ASTNode> T createNode(Class<T> type, Consumer<Memory> onInterpret, Function<Integer, List<String>> onCompile){
        T result = mock(type);
        doAnswer(invocationOnMock ->{
            if(onInterpret == null)
                throw new Exception("Missing interpretation function for mock");
            onInterpret.accept(invocationOnMock.getArgument(0));
            return null;
        }).when(result).interpret(any(Memory.class));
        doAnswer(invocationOnMock -> {
            if(onCompile == null)
                throw new Exception("Missing compile function for mock");
            return onCompile.apply(invocationOnMock.getArgument(0));
        }).when(result).compile(any(Integer.class));

        return result;
    }

    /**
     * Creates a mock to mimic an evaluable node
     * @param type class of node to create
     * @param onInterpret function called when the "interpret" method is called from the mock
     * @param onCompile function called when the "compile" method is called from the mock
     * @param onEval function called when the "eval" method is called from the mock
     * @return mock created
     * @param <T> class of node to create, defined by the [type] parameter
     */
    public static<T extends ASTNode & EvaluableNode> T createEvalNode(
            Class<T> type,
            Consumer<Memory> onInterpret,
            Function<Integer, List<String>> onCompile,
            Function<Memory, Value> onEval
    ){
        T result = createNode(type,  onInterpret, onCompile);
        doAnswer(invocationOnMock ->  {
            if(onEval == null)
                throw new Exception("Missing evaluation function for mock");
            return onEval.apply(invocationOnMock.getArgument(0));
        }).when(result).eval(any(Memory.class));
        return result;
    }

    /**
     * Creates a mock to mimic an evaluable node
     * @param type class of node to create
     * @param onInterpret function called when the "interpret" method is called from the mock
     * @param onCompile function called when the "compile" method is called from the mock
     * @param onWithdraw function called when the "withdrawInterpret" method is called from the mock
     * @return mock created
     * @param <T> class of node to create, defined by the [type] parameter
     */
    public static<T extends ASTNode & WithdrawalNode> T createWithdrawNode(
            Class<T> type,
            Consumer<Memory> onInterpret,
            Function<Integer, List<String>> onCompile,
            Consumer<Memory> onWithdraw,
            Function<Integer, List<String>> onWithdrawCompile
    ){
        T result = createNode(type,  onInterpret, onCompile);
        doAnswer(invocationOnMock ->  {
            if(onWithdraw == null)
                throw new Exception("Missing withdraw function for mock");
            onWithdraw.accept(invocationOnMock.getArgument(0));
            return null;
        }).when(result).withdrawInterpret(any(Memory.class));
        doAnswer(invocationOnMock ->  {
            if(onWithdrawCompile == null)
                throw new Exception("Missing withdraw function for mock");
            return onWithdrawCompile.apply(invocationOnMock.getArgument(0));
        }).when(result).withdrawCompile(any(Integer.class));
        return result;
    }
}
