module fr.ufrst.m1info.pvm.group5.driver {
    requires javafx.controls;
    requires javafx.fxml;
    requires fr.ufrst.m1info.pvm.group5.memory;
    requires fr.ufrst.m1info.pvm.group5.interpreter;
    opens fr.ufrst.m1info.pvm.group5.driver to javafx.fxml;

    exports fr.ufrst.m1info.pvm.group5.driver;
}