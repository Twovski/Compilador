package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/Note.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Proyecto");
        stage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResource("/resources/images/faq.png")).toExternalForm())
        );
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }

}
