module org.example.putscanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires sikulixapi;
    requires java.desktop;

    opens org.example.putscanner to javafx.fxml;
    exports org.example.putscanner;
    exports org.example.putscanner.fxmlControllers;
    opens org.example.putscanner.fxmlControllers to javafx.fxml;
}