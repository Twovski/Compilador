package com.example;

import com.example.models.Syntactic.SyntaxRegex;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainFX extends Application implements SyntaxRegex {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/Note.fxml"));
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
        String a = "a";
        String b= "b";
        System.out.println();

    }

    public static void main(String[] args) {
        launch();
    }

}
