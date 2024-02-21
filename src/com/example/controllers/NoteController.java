package com.example.controllers;

import com.example.models.Scanner.Lexer;
import com.example.models.Scanner.Token;
import com.example.models.Note.CodeBlock;
import com.example.models.Parser.Syntax;
import com.example.models.Semantic.Semantic;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NoteController implements Initializable {
    @FXML
    private BorderPane window;
    @FXML
    private MenuItem close, save, saveAs, parser, scanner, semantic;
    @FXML
    private TableColumn<Token, String> columnLexema, columnToken;
    @FXML
    private TableView<Token> tableToken;
    @FXML
    private ImageView image;
    @FXML
    private Label messageParser, messageSemantic;
    Lexer lexer;
    Syntax syntax;
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
        scanner.setDisable(true);
        parser.setDisable(true);
        semantic.setDisable(true);
    }

    @FXML
    void actionClose() {
        setProgram(image);
        close.setDisable(true);
        save.setDisable(true);
        saveAs.setDisable(true);
        scanner.setDisable(true);
        parser.setDisable(true);
        semantic.setDisable(true);
        observableList.setAll(new ArrayList<>());
        messageParser.setText("");
    }

    @FXML
    void actionExit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void actionNew() {
        CodeBlock codeBlock = new CodeBlock(null, "Nuevo archivo");
        if(!Objects.equals(getProgram(), codeBlock))
            setProgram(codeBlock);

        cleanWindow();
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
        setProgram(codeBlock);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null)
            codeBlock.appendText(line + "\n");

        cleanWindow();
    }

    @FXML
    void actionSave() throws IOException {
        CodeBlock codeBlock = (CodeBlock) getProgram();
        if(codeBlock.getPath() != null){
            writeFile(codeBlock);
            return;
        }
        actionSaveAs();
    }

    @FXML
    void actionSaveAs() throws IOException {
        CodeBlock codeBlock = (CodeBlock) getProgram();
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
    void actionScanner() {
        CodeBlock codeBlock = (CodeBlock) getProgram();
        observableList.setAll(new ArrayList<>());
        lexer = new Lexer();
        lexer.run(codeBlock.getText());
        observableList.addAll(lexer.getTokenList());
        tableToken.refresh();
        messageParser.setText("");
        messageSemantic.setText("");
    }

    @FXML
    void actionParser() {
        actionScanner();
        syntax = new Syntax(lexer.getTokenList());
        try {
            syntax.run();
            messageParser.setText("Syntax successful!");
            messageSemantic.setText("");
        }catch (Exception e){
            messageParser.setText("Syntax error!");
        }
    }

    @FXML
    void actionSemantic(){
        actionParser();
        if(!syntax.isComplete())
            return;

        Semantic _semantic = new Semantic(lexer.getTokenList());
        try {
            _semantic.run();
            messageSemantic.setText("Semantic successful!");
        } catch (Exception e) {
            messageSemantic.setText("Semantic error!");
        }
    }

    private void cleanWindow() {
        close.setDisable(false);
        save.setDisable(false);
        saveAs.setDisable(false);
        scanner.setDisable(false);
        parser.setDisable(false);
        semantic.setDisable(false);
        observableList.setAll(new ArrayList<>());
        messageParser.setText("");
    }

    private void writeFile(CodeBlock codeBlock) throws IOException {
        FileWriter fileWriter = new FileWriter(codeBlock.getPath(), false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(codeBlock.getText(), 0, codeBlock.getText().length());
        bufferedWriter.close();
    }

    private Node getProgram() {
        GridPane grid = (GridPane) window.getCenter();
        ObservableList<Node> children = grid.getChildren();
        Node result = null;
        for (Node child : children){
            int row = GridPane.getRowIndex(child) == null ? 0 : GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child) == null ? 0 : GridPane.getColumnIndex(child);
            if(row == 1 && column == 0){
                result = child;
                break;
            }
        }
        return result;
    }

    private void setProgram(Node node){
        GridPane grid = (GridPane) window.getCenter();
        grid.getChildren().remove(getProgram());
        grid.add(node, 0, 1);
    }
}
