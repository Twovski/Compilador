package com.example.Controllers;

import com.example.Models.Lexer;
import com.example.Models.Note.CodeBlock;
import com.example.Models.Token;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LexicalController implements Initializable {
    @FXML
    private TableView<Token> tableView;
    @FXML
    private TableColumn<Token, String> lexemaColumn;
    @FXML
    private TableColumn<Token, String> tokenColumn;
    ObservableList<Token> observableList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList = FXCollections.observableArrayList();
        tokenColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        lexemaColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.setItems(observableList);
    }

    public void reloadTable(CodeBlock codeBlock){
        observableList.setAll(new ArrayList<>());
        Lexer lexer = new Lexer();
        lexer.tokenize(codeBlock.getText());
        observableList.addAll(lexer.getTokenList());
        tableView.refresh();
    }
}
