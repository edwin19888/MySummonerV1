package com.webin.mysummonerv1;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener,AlertDialogRadio.AlertNegativeListener {

    EditText editTextSummoner;
    ImageView imageViewChampion;

    CharSequence[] values = {"Korea","Lationamerica Norte","Lationamerica Sur","Brazil","North America"};
    int position = 0;
    int mostrarServer;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity


        imageViewChampion = (ImageView) findViewById(R.id.ivChampions);
        imageViewChampion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ChampionsActivity.class);
                startActivity(i);
            }
        });

        if (mostrarServer == 0) {
            openDialogServer();
        }

        final String[] summoner = new String[1];

        editTextSummoner = (EditText) findViewById(R.id.etSummoner);
        editTextSummoner.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if  ((actionId == EditorInfo.IME_ACTION_SEARCH)) {


                    summoner[0] = editTextSummoner.getText().toString();
                    if(summoner[0].length() > 0){
                        Mensaje(summoner[0]);
                    }else{
                        Mensaje("Debe ingresar un usuario");
                    }


                }
                return false;
            }
        });
    }

    public void Mensaje(String msj){
        Toast.makeText(MainActivity.this,""+ msj,Toast.LENGTH_LONG).show();
    }


    public void openDialogServer() {
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();

        /** Creating a bundle object to store the selected item's index */
        Bundle b  = new Bundle();

        /** Storing the selected item's index in the bundle object */
        b.putInt("position", position);

        /** Setting the bundle object to the dialog fragment object */
        alert.setArguments(b);

        /** Creating the dialog fragment object, which will in turn open the alert dialog window */
        alert.show(manager, "alert_dialog_radio");
    }


    @Override
    public void onPositiveClick(int position) {
        this.position = position;

        /** Getting the reference of the textview from the main layout */
        String serverSelection = Android.code[this.position];

        Toast.makeText(MainActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.commit();
    }

    @Override
    public void onNegativeClick(int position){
        this.position = 0;


        Toast.makeText(MainActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);
        String serverSelection = "Korea";
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.commit();
    }
}
