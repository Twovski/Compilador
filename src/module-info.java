module Compilador {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;

    opens com.example to javafx.fxml;
    opens com.example.Controllers to javafx.fxml;
    opens com.example.Views to javafx.graphics;

    exports com.example.Controllers;
    exports com.example;
}