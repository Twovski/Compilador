package com.example.Controllers;

import com.example.Models.CodeBlock;
import com.example.Models.WindowKeys;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.io.*;
import java.net.URL;
import java.util.*;

public class NoteController implements Initializable, WindowKeys {
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView imageView;
    @FXML
    private MenuItem openFile, closeFile, newFile, saveFile, lexical, syntactic;
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tabPane.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/resources/styles/tab-panel.css")).toExternalForm()
        );
        closeFile.setDisable(true);
        saveFile.setDisable(true);
        lexical.setDisable(true);
        syntactic.setDisable(true);
    }

    @FXML
    public void showLexical() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Views/Lexical.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Lexical");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(borderPane.getScene().getWindow());
        stage.showAndWait();
    }
    @FXML
    public void showSyntactic(){

    }

    @FXML
    public void actionOpenFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos faq", "*.faq")
        );
        Stage stage = (Stage) borderPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file == null)
            return;

        borderPane.setCenter(tabPane);
        CodeArea codeArea = addTabCode(file.getName(), file.getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null)
            codeArea.appendText(line + "\n");

        closeFile.setDisable(false);
        saveFile.setDisable(false);
        lexical.setDisable(false);
        syntactic.setDisable(false);
    }
    @FXML
    public void actionCloseFile(){
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
        if(tabPane.getTabs().size() != 0)
            return;

        borderPane.setCenter(imageView);
        closeFile.setDisable(true);
        saveFile.setDisable(true);
        lexical.setDisable(true);
        syntactic.setDisable(true);
    }
    @FXML
    public void actionNewFile(){
        if(borderPane.getCenter() != tabPane)
            borderPane.setCenter(tabPane);
        addTabCode("Nuevo archivo", null);

        closeFile.setDisable(false);
        saveFile.setDisable(false);
        lexical.setDisable(false);
        syntactic.setDisable(false);
    }
    @FXML
    public void actionSaveFile() throws IOException {
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        @SuppressWarnings("unchecked")
        VirtualizedScrollPane<CodeBlock> scrollPane = (VirtualizedScrollPane<CodeBlock>) tab.getContent();
        CodeBlock codeBlock = scrollPane.getContent();

        if(codeBlock.getPath() != null){
            writeFile(codeBlock);
            tabPane.getSelectionModel().getSelectedItem().setText(codeBlock.getTitle());
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos faq", "*.faq")
        );
        Stage stage = (Stage) borderPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file == null)
            return;
        codeBlock.setPath(file.getAbsolutePath());
        codeBlock.setTitle(file.getName());
        tabPane.getSelectionModel().getSelectedItem().setText(codeBlock.getTitle());
        writeFile(codeBlock);
    }

    @FXML
    public void actionExit(){
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void keyWindow(KeyEvent event) throws IOException {
        if(KEY_CTRL_S.match(event)){
            if(imageView == borderPane.getCenter())
                return;
            actionSaveFile();
        }
        if(KEY_CTRL_N.match(event))
            actionNewFile();

        if(KEY_CTRL_O.match(event))
            actionOpenFile();

        if(KEY_CTRL_F4.match(event))
            actionCloseFile();

        if(KEY_ALT_F4.match(event))
            actionExit();

    }
    private void writeFile(CodeBlock codeBlock) throws IOException {
        FileWriter fileWriter = new FileWriter(codeBlock.getPath(), false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(codeBlock.getText(), 0, codeBlock.getText().length());
        bufferedWriter.close();
    }
    private CodeBlock addTabCode(String text, String path){
        CodeBlock codeBlock = new CodeBlock(path, text);
        VirtualizedScrollPane<CodeBlock> scrollPane = new VirtualizedScrollPane<>(codeBlock);
        Tab tab = new Tab(text, scrollPane);
        tab.setOnCloseRequest(event -> actionCloseFile());
        tabPane.getTabs().add(tab);
        return codeBlock;
    }
}
