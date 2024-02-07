module Compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;

    opens com.example to javafx.fxml;
    opens com.example.views to javafx.graphics;
    opens com.example.controllers to javafx.fxml;
    opens com.example.models.Lexical to javafx.graphics;

    exports com.example.models.Lexical;
    exports com.example.controllers;
    exports com.example;

}