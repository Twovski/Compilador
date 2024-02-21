module Compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires org.fxmisc.flowless;
    requires EvalEx;

    opens com.example to javafx.fxml;
    opens com.example.views to javafx.graphics;
    opens com.example.controllers to javafx.fxml;
    opens com.example.models.Scanner to javafx.graphics;
    opens com.example.models.Parser to javafx.graphics;

    exports com.example.models.Parser;
    exports com.example.models.Scanner;
    exports com.example.controllers;
    exports com.example;

}