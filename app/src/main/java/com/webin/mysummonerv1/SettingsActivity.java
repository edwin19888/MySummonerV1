package com.webin.mysummonerv1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener,AlertDialogRadio.AlertNegativeListener {

    ImageView imageViewChampion,imageViewSearch;
    TextView textViewCurrentServer;
    ImageView imageViewCurrentServer;
    CharSequence[] values = {"Korea","Lationamerica Norte","Lationamerica Sur","Brazil","North America"};
    int position = 0;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        setTitle("Información");

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Korea");

        textViewCurrentServer = (TextView) findViewById(R.id.TextViewCurrenServer);
        textViewCurrentServer.setText(serverName);

        imageViewChampion = (ImageView) findViewById(R.id.ivChampions);
        imageViewChampion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this,ChampionsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        imageViewSearch = (ImageView) findViewById(R.id.ivSearchSummoner);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this,PrincipalActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        imageViewCurrentServer = (ImageView) findViewById(R.id.imageViewCurrentServer);

        imageViewCurrentServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogServer();
            }
        });
    }

    public void openDialogServer() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int idServer = prefs.getInt("idServer",0);
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();

        /** Creating a bundle object to store the selected item's index */
        Bundle b  = new Bundle();

        /** Storing the selected item's index in the bundle object */
        b.putInt("position", idServer);

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

        //Toast.makeText(SettingsActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.putInt("idServer",position);
        editor.commit();

        textViewCurrentServer.setText(serverSelection);
    }

    @Override
    public void onNegativeClick(int position){
        /*
        this.position = 0;

        String serverSelection = "Korea";
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.commit();
        */
    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }
}
