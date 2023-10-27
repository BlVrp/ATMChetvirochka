module com.example.atmchetvirochka {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.compiler;
    requires java.sql;

    opens com.example.atmchetvirochka to javafx.fxml;
    exports com.example.atmchetvirochka;
    exports com.example.atmchetvirochka.controller;
    opens com.example.atmchetvirochka.controller to javafx.fxml;
}