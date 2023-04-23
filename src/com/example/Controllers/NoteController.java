package com.example.Controllers;

import com.example.Models.FAQAsync;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private CodeArea codeArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FAQAsync faqAsync = new FAQAsync(executor, codeArea);
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                int position = codeArea.getCaretPosition();
                codeArea.replaceText(position, position, "    ");
                event.consume();
            }
        });

        codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .retainLatestUntilLater(executor)
                .supplyTask(faqAsync::computeHighlightingAsync)
                .awaitLatest(codeArea.multiPlainChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(faqAsync::applyHighlighting);

        VirtualizedScrollPane<CodeArea> virtualScrollPane = new VirtualizedScrollPane<>(codeArea);
        borderPane.setCenter(virtualScrollPane);
    }
}
