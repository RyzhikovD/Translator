package ru.sberbankmobile.translator;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText sourceEditText;
    private TextView resultTextView;
    private String[] cyrillic;
    private String[] latin;
    private PasswordsHelper helper;

    ImageButton copyButton;

    ClipboardManager clipboardManager;
    ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.edit_source);
        resultTextView = findViewById(R.id.text_result);

        cyrillic = getResources().getStringArray(R.array.cyrillic);
        latin = getResources().getStringArray(R.array.latin);

        helper = new PasswordsHelper(cyrillic, latin);

        copyButton = findViewById(R.id.copy_button);

        clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = resultTextView.getText().toString();
                clipData = ClipData.newPlainText("text",text);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(),"Text Copied ",Toast.LENGTH_SHORT).show();
            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultTextView.setText(helper.convert(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
