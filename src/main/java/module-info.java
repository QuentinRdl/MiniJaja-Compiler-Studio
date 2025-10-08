module fr.ufrst.m1info.gl.compgl.compgl {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.antlr.antlr4.runtime;


    opens fr.ufrst.m1info.gl.compgl.compgl to javafx.fxml;
    exports fr.ufrst.m1info.gl.compgl.compgl;
}