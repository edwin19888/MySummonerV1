package com.webin.mysummonerv1;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_choose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClick(view);
            }
        });
    }

    public void DialogClick(View view){
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana.setView(R.layout.layout_custom_dialog);
        ventana.setTitle("Chooser Server");

        ventana.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Confirmo",Toast.LENGTH_LONG).show();
            }
        });

        ventana.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_LONG).show();
            }
        });
        ventana.show();
    }

}
