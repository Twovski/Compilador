package com.example.models.Note;

import javafx.concurrent.Task;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;

public class BlockAsync implements Keywords {
    public ExecutorService executor;
    public CodeArea codeArea;

    public BlockAsync(ExecutorService executor, CodeArea codeArea) {
        this.executor = executor;
        this.codeArea = codeArea;
    }

    @Override
    public Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<>() {
            @Override
            protected StyleSpans<Collection<String>> call() {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    @Override
    public void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    @Override
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass;
            if (matcher.group("KEYWORD") != null)
                styleClass = "keyword";
            else if (matcher.group("OPERATOR") != null)
                styleClass = "operator";
            else if (matcher.group("PAREN") != null)
                styleClass = "paren";
            else if (matcher.group("BRACE") != null)
                styleClass = "brace";
            else if (matcher.group("SEMICOLON") != null)
                styleClass = "semicolon";
            else if(matcher.group("NUMBER") != null)
                styleClass = "number";
            else if(matcher.group("BOOLEAN") != null)
                styleClass = "boolean";
            else{
                assert false : "Expresión regular no válida";
                styleClass = null;
            }

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
