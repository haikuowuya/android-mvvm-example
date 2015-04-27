package de.rheinfabrik.mvvm_example.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Class used to show/hide the keyboard.
 */
public class KeyboardHandler {

    /**
     * Shows the keyboard.
     */
    public static void showKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (editText.requestFocus()) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Hides the keyboard.
     */
    public static void hideKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
