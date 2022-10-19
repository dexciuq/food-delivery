module com.example.delivery {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.delivery to javafx.fxml;
    exports com.example.delivery;
    exports com.example.delivery.controller;
    opens com.example.delivery.controller to javafx.fxml;
    exports com.example.delivery.controller.menu;
    opens com.example.delivery.controller.menu to javafx.fxml;
}