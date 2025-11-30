module fr.ufrst.m1info.pvm.group5.driver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign;

    requires fr.ufrst.m1info.pvm.group5.memory;
    requires fr.ufrst.m1info.pvm.group5.interpreter;
    requires fr.ufrst.m1info.pvm.group5.compiler;
    opens fr.ufrst.m1info.pvm.group5.driver to javafx.fxml;

    exports fr.ufrst.m1info.pvm.group5.driver;
}