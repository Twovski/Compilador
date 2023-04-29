package com.example.Models;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public interface WindowKeys {
    KeyCombination KEY_CTRL_S = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    KeyCombination KEY_CTRL_N = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    KeyCombination KEY_CTRL_O = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
    KeyCombination KEY_CTRL_F4 = new KeyCodeCombination(KeyCode.F4, KeyCombination.CONTROL_DOWN);
    KeyCombination KEY_ALT_F4 = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
    KeyCombination KEY_CTRL_L = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
}
