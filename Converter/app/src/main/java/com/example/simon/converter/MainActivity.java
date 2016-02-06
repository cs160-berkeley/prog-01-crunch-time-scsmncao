package com.example.simon.converter;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Hashtable;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Hashtable<Integer, String> tableOfIds;
    private Hashtable<String, Double> tableOfConversions;
    private Hashtable<String, Integer> tableOfWeightConversions;
    int changingText = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("myfitnessconverter");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText weightText = (EditText) findViewById(R.id.editText26);
        weightText.setRawInputType(Configuration.KEYBOARD_12KEY);
        weightText.setOnKeyListener(new View.OnKeyListener() {
            /**
             * This listens for the user to press the enter button on
             * the keyboard and then hides the virtual keyboard
             */
            public boolean onKey(View arg0, int arg1, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (arg1 == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(weightText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        tableOfIds = new Hashtable();
        tableOfConversions = new Hashtable();
        tableOfWeightConversions = new Hashtable();

        tableOfIds.put(R.id.editText, "calories");
        tableOfIds.put(R.id.editText3, "pushups");
        tableOfIds.put(R.id.editText5, "situps");
        tableOfIds.put(R.id.editText7, "squats");
        tableOfIds.put(R.id.editText9, "leglifts");
        tableOfIds.put(R.id.editText11, "planks");
        tableOfIds.put(R.id.editText13, "jumpingjacks");
        tableOfIds.put(R.id.editText15, "pullups");
        tableOfIds.put(R.id.editText17, "cycling");
        tableOfIds.put(R.id.editText19, "walking");
        tableOfIds.put(R.id.editText21, "jogging");
        tableOfIds.put(R.id.editText23, "swimming");
        tableOfIds.put(R.id.editText25, "stairs");


        tableOfConversions.put("pushups", 350.0);
        tableOfConversions.put("situps", 200.0);
        tableOfConversions.put("squats", 225.0);
        tableOfConversions.put("leglifts", 25.0);
        tableOfConversions.put("planks", 25.0);
        tableOfConversions.put("jumpingjacks", 10.0);
        tableOfConversions.put("pullups", 100.0);
        tableOfConversions.put("cycling", 12.0);
        tableOfConversions.put("walking", 20.0);
        tableOfConversions.put("jogging", 12.0);
        tableOfConversions.put("swimming", 13.0);
        tableOfConversions.put("stairs", 15.0);

        tableOfWeightConversions.put("pushups", 6);
        tableOfWeightConversions.put("situps", 3);
        tableOfWeightConversions.put("squats", 7);
        tableOfWeightConversions.put("leglifts", 5);
        tableOfWeightConversions.put("planks", 4);
        tableOfWeightConversions.put("jumpingjacks", 3);
        tableOfWeightConversions.put("pullups", 8);
        tableOfWeightConversions.put("cycling", 7);
        tableOfWeightConversions.put("walking", 3);
        tableOfWeightConversions.put("jogging", 5);
        tableOfWeightConversions.put("swimming", 5);
        tableOfWeightConversions.put("stairs", 11);

        for (Integer key: tableOfIds.keySet()) {
            final EditText text = (EditText) findViewById(key);
            text.setRawInputType(Configuration.KEYBOARD_12KEY);
            text.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (changingText == 1) {
                        return;
                    }
                    changingText = 1;
                    View currentView = getCurrentFocus();
                    EditText currentTextView = (EditText) findViewById(currentView.getId());
                    String currentText = currentTextView.getText().toString();

                    EditText weightText = (EditText) findViewById(R.id.editText26);
                    String weightString = weightText.getText().toString();
                    if (Objects.equals(weightString, "")) {
                        weightString = "150";
                    }
                    int weightFactor = (Integer.parseInt(weightString) - 150) / 10;

                    if (Objects.equals(currentText, "")) {
                        currentText = "0";
                    }
                    long calories = 0;
                    String exercise = tableOfIds.get(currentView.getId());
                    if (!Objects.equals(exercise, "calories")) {
                        int repsOrMin = Integer.parseInt(currentText);
                        calories = Math.round(repsOrMin / tableOfConversions.get(exercise) * 100);
                        calories = calories + Math.round(weightFactor * tableOfWeightConversions.get(exercise) * calories / 100.0);
                        ((EditText) findViewById(R.id.editText)).setText(calories + "");
                    } else {
                        calories = Integer.parseInt(currentText);
                    }

                    for (Integer newKey : tableOfIds.keySet()) {
                        if (newKey == currentView.getId() || tableOfIds.get(newKey) == "calories") {
                            continue;
                        }
                        EditText newText = (EditText) findViewById(newKey);
                        String typeOfExercise = tableOfIds.get(newKey);
                        double convertedCalories = calories - weightFactor * tableOfWeightConversions.get(typeOfExercise) * calories / 100.0;
                        long newCalories = Math.round(convertedCalories / 100.0 * tableOfConversions.get(typeOfExercise));
                        newText.setText(newCalories + "");
                    }
                    changingText = 0;
                }
            });
            text.setOnKeyListener(new View.OnKeyListener() {
                /**
                 * This listens for the user to press the enter button on
                 * the keyboard and then hides the virtual keyboard
                 */
                public boolean onKey(View arg0, int arg1, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (arg1 == KeyEvent.KEYCODE_ENTER)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
