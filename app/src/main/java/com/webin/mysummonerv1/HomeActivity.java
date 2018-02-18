package com.webin.mysummonerv1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener,AlertDialogRadio.AlertNegativeListener {

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    int position = 0;

    ImageView imageViewChampion,imageViewSetting,ivCloseApp;
    RelativeLayout rlHome;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        int mostrarServer = prefs.getInt("mostrarServer", 0);
        String serverName = prefs.getString("serverName","Lationamerica Norte");
        final String plataforma = prefs.getString("plataforma","la1");
        int idServer = prefs.getInt("idServer",0);
        String rss = prefs.getString("rss","lan");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//no girar activity

        rlHome = (RelativeLayout) findViewById(R.id.rlHome);
        ivCloseApp = (ImageView)  findViewById(R.id.ivCloseApp);
        ivCloseApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CerrarApp();
            }
        });

        imageViewChampion = (ImageView) findViewById(R.id.ivChampions);
        imageViewChampion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlHome.setVisibility(View.INVISIBLE);
                Intent i = new Intent(HomeActivity.this,ChampionsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        });

        imageViewSetting = (ImageView) findViewById(R.id.ivSettings);
        imageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlHome.setVisibility(View.INVISIBLE);
                Intent s = new Intent(HomeActivity.this,SettingsActivity.class);
                startActivity(s);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        });
        if (mostrarServer == 0) {
            openDialogServer();
        }else{
            CargarRss(rss);
        }
    }

    private void CerrarApp() {
        super.onBackPressed();
        finish();
    }

    public void CargarRss(String rss){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ReadRss readRss = new ReadRss(this, recyclerView,rss);
        readRss.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, OtherActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialogServer() {
        /** Getting the fragment manager */
        FragmentManager manager = getFragmentManager();

        /** Instantiating the DialogFragment class */
        AlertDialogRadio alert = new AlertDialogRadio();
        alert.setCancelable(false);

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
        String plataforma,rss;

        /** Getting the reference of the textview from the main layout */
        String serverSelection = Android.code[this.position];
        switch (this.position){
            case 0:
                plataforma = "la1";
                rss = "lan";
                break;
            case 1:
                plataforma = "la2";
                rss = "las";
                break;
            default:
                plataforma = "la1";
                rss = "lan";
        }

        //Toast.makeText(PrincipalActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.putInt("idServer",position);
        editor.putString("plataforma",plataforma);
        editor.putString("rss",rss);
        editor.commit();
        CargarRss(rss);
    }

    @Override
    public void onNegativeClick(int position){
        this.position = 0;
        String plataforma = "la1",rss="lan";

        //Toast.makeText(HomeActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        //tv.setText("Your Choice : " + Android.code[this.position]);
        String serverSelection = "Lationamerica Norte";
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("serverName", serverSelection);
        editor.putInt("mostrarServer", 1);
        editor.putInt("idServer",position);
        editor.putString("plataforma",plataforma);
        editor.putString("rss",rss);
        editor.commit();
        CargarRss(rss);
    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            Log.d("SALIO"," SUPUESTAMENTE DE TODO");
            super.onBackPressed();
            finish();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }
}
