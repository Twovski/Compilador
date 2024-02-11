package com.example.controllers;

import com.example.models.Lexical.Lexer;
import com.example.models.Lexical.Token;
import com.example.models.Note.CodeBlock;
import com.example.models.Syntactic.Syntax;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NoteController implements Initializable {
    @FXML
    private BorderPane window;
    @FXML
    private MenuItem close, save, saveAs, syntactic, lexical;
    @FXML
    private TableColumn<Token, String> columnLexema, columnToken;
    @FXML
    private TableView<Token> tableToken;
    @FXML
    private ImageView image;
    @FXML
    private Label message;
    Lexer lexer;
    ObservableList<Token> observableList;
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = FXCollections.observableArrayList();
        columnToken.setCellValueFactory(new PropertyValueFactory<>("value"));
        columnLexema.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableToken.setItems(observableList);
        close.setDisable(true);
        save.setDisable(true);
        saveAs.setDisable(true);
        lexical.setDisable(true);
        syntactic.setDisable(true);
    }

    @FXML
    void actionClose() {
        window.setCenter(image);
        close.setDisable(true);
        save.setDisable(true);
        saveAs.setDisable(true);
        lexical.setDisable(true);
        syntactic.setDisable(true);
        observableList.setAll(new ArrayList<>());
        message.setText("");
    }

    @FXML
    void actionExit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void actionNew() {
        CodeBlock codeBlock = new CodeBlock(null, "Nuevo archivo");
        if(!window.getCenter().equals(codeBlock))
            window.setCenter(codeBlock);

        close.setDisable(false);
        save.setDisable(false);
        saveAs.setDisable(false);
        lexical.setDisable(false);
        syntactic.setDisable(false);
        observableList.setAll(new ArrayList<>());
        message.setText("");
    }

    @FXML
    void actionOpen() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos faq", "*.faq")
        );
        Stage stage = (Stage) window.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file == null)
            return;

        CodeBlock codeBlock = new CodeBlock(file.getAbsolutePath(), file.getName());
        window.setCenter(codeBlock);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null)
            codeBlock.appendText(line + "\n");

        close.setDisable(false);
        save.setDisable(false);
        saveAs.setDisable(false);
        lexical.setDisable(false);
        syntactic.setDisable(false);
        observableList.setAll(new ArrayList<>());
        message.setText("");
    }

    @FXML
    void actionSave() throws IOException {
        CodeBlock codeBlock = (CodeBlock) window.getCenter();
        if(codeBlock.getPath() != null){
            writeFile(codeBlock);
            return;
        }
        actionSaveAs();
    }

    @FXML
    void actionSaveAs() throws IOException {
        CodeBlock codeBlock = (CodeBlock) window.getCenter();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos faq", "*.faq")
        );
        Stage stage = (Stage) window.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file == null)
            return;
        codeBlock.setPath(file.getAbsolutePath());
        codeBlock.setTitle(file.getName());
        writeFile(codeBlock);
    }

    @FXML
    void actionLexical() {
        CodeBlock codeBlock = (CodeBlock) window.getCenter();
        observableList.setAll(new ArrayList<>());
        lexer = new Lexer();
        lexer.tokenize(codeBlock.getText());
        observableList.addAll(lexer.getTokenList());
        tableToken.refresh();
    }

    @FXML
    void actionSyntatic() {
        actionLexical();
        Syntax syntax = new Syntax(lexer.getTokenList());
        try {
            syntax.parse();
            message.setText("Syntax successful!");
        }catch (Exception e){
            message.setText("Syntax error!");
        }
    }

    private void writeFile(CodeBlock codeBlock) throws IOException {
        FileWriter fileWriter = new FileWriter(codeBlock.getPath(), false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(codeBlock.getText(), 0, codeBlock.getText().length());
        bufferedWriter.close();
    }
}
