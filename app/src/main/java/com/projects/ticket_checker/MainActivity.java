    package com.projects.ticket_checker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

    public class MainActivity extends AppCompatActivity {

    Button btScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btScan=findViewById(R.id.bt_scan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator= new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("for flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(capture.class);
                intentIntegrator.initiateScan();

            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            String string=intentResult.getContents().toString();

            ClipboardManager clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData= ClipData.newPlainText("label",string);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this,"Your message is copied to clipboard",Toast.LENGTH_SHORT).show();


            if (intentResult.getContents() != null){
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Result");
                builder.setMessage(intentResult.getContents());

                builder.setPositiveButton("next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        openActivity2();
                    }
                });
                builder.show();

            }else {
                Toast.makeText(getApplicationContext(),"oops...You did not scan anything",Toast.LENGTH_SHORT).show();

            }

        }

        public void openActivity2(){

        Intent intent=new Intent(this,MainActivity2.class);
        startActivity(intent);
        }
    }