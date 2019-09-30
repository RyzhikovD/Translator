package ru.sberbankmobile.translator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText sourceEditText;
    private TextView resultTextView;
    private TextView seekBarText;
    private TextView generatedPasswordTextView;
    private View qualityCircle;
    private TextView passwordQualityTextView;
    private String[] cyrillic;
    private String[] latin;
    private PasswordsHelper helper;

    private ImageButton copyButton;
    private ClipboardManager clipboardManager;
    private ClipData clipData;

    private SeekBar seekBar;
    private CheckBox useUppercase;
    private CheckBox useNumerals;
    private CheckBox useSpecialCharacters;

    private Button generatePasswordButton;
    private ImageButton copyPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cyrillic = getResources().getStringArray(R.array.cyrillic);
        latin = getResources().getStringArray(R.array.latin);

        sourceEditText = findViewById(R.id.edit_source);
        resultTextView = findViewById(R.id.text_result);
        seekBarText = findViewById(R.id.seek_bar_text);
        copyButton = findViewById(R.id.copy_button);
        seekBar = findViewById(R.id.seek_bar);
        useUppercase = findViewById(R.id.checkbox_uppercase);
        useNumerals = findViewById(R.id.checkbox_numerals);
        useSpecialCharacters = findViewById(R.id.checkbox_special_characters);
        generatePasswordButton = findViewById(R.id.generate_password);
        copyPasswordButton = findViewById(R.id.copy_generated_password_button);
        generatedPasswordTextView = findViewById(R.id.generated_password_text_view);
        passwordQualityTextView = findViewById(R.id.password_quality);
        qualityCircle = findViewById(R.id.quality_circle);

        helper = new PasswordsHelper(cyrillic, latin);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = resultTextView.getText().toString();
                clipData = ClipData.newPlainText("text", text);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(), "Text Copied ", Toast.LENGTH_SHORT).show();
            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(helper.convert(s));
                String quality = PasswordsHelper.quality(resultTextView.getText().toString());
                passwordQualityTextView.setText(quality);
                switch (quality) {
                    case "Слабый пароль":
                        qualityCircle.setBackgroundResource(R.drawable.rose_circle);
                        break;
                    case "Нормальный пароль":
                        qualityCircle.setBackgroundResource(R.drawable.orange_circle);
                        break;
                    case "Хороший пароль":
                        qualityCircle.setBackgroundResource(R.drawable.yellow_circle);
                        break;
                    case "Отличный пароль":
                        qualityCircle.setBackgroundResource(R.drawable.green_circle);
                        break;
                    default:
                        qualityCircle.setBackgroundResource(R.drawable.red_circle);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        seekBarText.setText("8 символов");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String characters = "8 + " + i + " = " + (8 + i) + " символов";
                seekBarText.setText(characters);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        copyPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = generatedPasswordTextView.getText().toString();
                clipData = ClipData.newPlainText("text", text);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(), "Password Copied ", Toast.LENGTH_SHORT).show();
            }
        });

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String generatedPassword = PasswordGenerator.generate(
                        useSpecialCharacters.isChecked(),
                        useUppercase.isChecked(),
                        useNumerals.isChecked(),
                        seekBar.getProgress() + 8);
                generatedPasswordTextView.setText(generatedPassword);
            }
        });
    }
}
