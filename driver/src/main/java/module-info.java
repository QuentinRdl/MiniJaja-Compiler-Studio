module fr.ufrst.m1info.gl.compgl.driver {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.ufrst.m1info.pvm.group5.driver to javafx.fxml;
    exports fr.ufrst.m1info.pvm.group5.driver;
}