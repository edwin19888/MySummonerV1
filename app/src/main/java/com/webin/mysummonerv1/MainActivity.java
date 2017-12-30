package com.webin.mysummonerv1;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener,AlertDialogRadio.AlertNegativeListener {

    Button btnDialog;
    AlertDialog alertDialog1;
    CharSequence[] values = {"Korea","Lationamerica Norte","Lationamerica Sur","Brazil","North America"};
    int position = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDialogServer();
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
        TextView tv = (TextView) findViewById(R.id.tv_android);

        Toast.makeText(MainActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        tv.setText("Your Choice : " + Android.code[this.position]);
    }

    @Override
    public void onNegativeClick(int position){
        this.position = 0;
        TextView tv = (TextView) findViewById(R.id.tv_android);

        Toast.makeText(MainActivity.this,"Check: "+Android.code[this.position],Toast.LENGTH_SHORT).show();
        /** Setting the selected android version in the textview */
        tv.setText("Your Choice : " + Android.code[this.position]);
    }
}
