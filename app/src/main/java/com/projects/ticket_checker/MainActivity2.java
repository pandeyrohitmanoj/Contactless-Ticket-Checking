package com.projects.ticket_checker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import org.w3c.dom.Text;

import java.security.GeneralSecurityException;

public class MainActivity2 extends AppCompatActivity {


    Button button1;
    EditText key,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        message=findViewById(R.id.t_view);
        button1=findViewById(R.id.decrypt);
        key=findViewById(R.id.et_key);

        Intent intent=getIntent();



    }

    public void decrypt(View view) {
        try {
            String decrypt= AESCrypt.decrypt(key.getText().toString(),message.getText().toString());
            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData= ClipData.newPlainText("label",decrypt);
            clipboardManager.setPrimaryClip(clipData);
            key.setText("");
            message.setText(decrypt);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }
}