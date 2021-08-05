package com.projects.new_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.scottyab.aescrypt.AESCrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    EditText key,message_et;
    TextView message;
    String encrypt;
    Button btGenerate;
    ImageView ivOutput;
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        key=findViewById(R.id.et_key);
        message_et=findViewById(R.id.et_message);
        message=findViewById(R.id.tv_message);
        btGenerate=findViewById(R.id.bt_generate);
        ivOutput=findViewById(R.id.iv_output);


        btGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText=encrypt.toString().trim();
                MultiFormatWriter writer=new MultiFormatWriter();
                BitMatrix matrix;
                Bitmap bitmap;
                try {

                    matrix = writer.encode(sText,BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder=new BarcodeEncoder();
                    bitmap=encoder.createBitmap(matrix);
                    ivOutput.setImageBitmap(bitmap);
                    InputMethodManager manager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    public void encrypt(View view) {
        try {
            String encrypted = AESCrypt.encrypt(key.getText().toString(), message_et.getText().toString());
            encrypt=encrypted;
            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData= ClipData.newPlainText("label",encrypted);
            clipboardManager.setPrimaryClip(clipData);
            key.setText("");
            message_et.setText(encrypted);
            Toast.makeText(this,"Your message is encrypted",Toast.LENGTH_SHORT).show();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }





}