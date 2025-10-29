package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.Nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.memory.Value;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * Static class to quickly create classes mocking Memory
 */
public class ASTMocks {

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
