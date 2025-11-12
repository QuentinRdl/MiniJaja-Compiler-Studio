module fr.ufrst.m1info.pvm.group5.ast {
    requires org.antlr.antlr4.runtime;
    requires fr.ufrst.m1info.pvm.group5.memory;
    requires java.desktop;

    exports fr.ufrst.m1info.pvm.group5.ast;
    opens fr.ufrst.m1info.pvm.group5.ast.Nodes;

    exports fr.ufrst.m1info.pvm.group5.ast.Instructions;
}