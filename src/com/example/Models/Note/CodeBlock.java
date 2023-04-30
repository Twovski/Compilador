package com.example.Models.Note;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CodeBlock extends CodeArea {
    private String title;
    private String path;
    public CodeBlock(String path, String title) {
        super();
        this.path = path;
        this.title = title;
        setUp();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    private void setUp(){
        getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource("/resources/styles/code-area.css")).toExternalForm()
        );

        ExecutorService executor = Executors.newSingleThreadExecutor();
        FAQAsync faqAsync = new FAQAsync(executor, this);
        setParagraphGraphicFactory(LineNumberFactory.get(this));
        addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                int position = getCaretPosition();
                replaceText(position, position, "    ");
                event.consume();
            }
        });

        multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .retainLatestUntilLater(executor)
                .supplyTask(faqAsync::computeHighlightingAsync)
                .awaitLatest(multiPlainChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(faqAsync::applyHighlighting);
    }
}
