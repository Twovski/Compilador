package com.example;

import com.example.models.Intermediate.Postfix;
import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.parser.ParseException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException, EvaluationException, ParseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/Note.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Compilador MiniFAQ");
        stage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResource("/resources/images/alien.png")).toExternalForm())
        );
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
