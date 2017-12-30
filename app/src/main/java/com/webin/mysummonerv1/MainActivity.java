package com.webin.mysummonerv1;

import java.util.ArrayList;
import java.util.List;
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

public class MainActivity extends AppCompatActivity {

    Button btnDialog;
    AlertDialog.Builder alertDialog;
    ArrayList<String> myList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = new ArrayList<String>();

        myList.add("India");
        myList.add("China");
        myList.add("South Africa");
        myList.add("USA");
        myList.add("UK");
        myList.add("Japan ");
        myList.add("Canada");

        alertDialog = new AlertDialog.Builder(MainActivity.this);

        btnDialog = (Button) findViewById(R.id.btn_choose);

        MostrarDialog();

    }

    public void MostrarDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        // create view for add item in dialog

        View convertView = (View) inflater.inflate(R.layout.row_server, null);

        // on dialog cancel button listner

        alertDialog.setNegativeButton("Cancel",

                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,

                                        int which) {
                        // TODO Auto-generated method stub

                    }

                });



        // add custom view in dialog

        alertDialog.setView(convertView);

        ListView lv = (ListView) convertView.findViewById(R.id.mylistview);

        final AlertDialog alert = alertDialog.create();

        alert.setTitle(" Select country...."); // Title

        MyAdapter myadapter = new MyAdapter(MainActivity.this,

                R.layout.listview_item, myList);

        lv.setAdapter(myadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                Toast.makeText(MainActivity.this,

                        "You have selected -: " + myList.get(position),

                        Toast.LENGTH_SHORT).show();

                alert.cancel();

            }

        });

        // show dialog

        alert.show();


    }

    class MainListHolder {
        private TextView tvText;
    }

    private class ViewHolder {
        TextView tvSname;
    }

    class MyAdapter extends ArrayAdapter<String> {

        LayoutInflater inflater;

        Context myContext;

        List<String> newList;

        public MyAdapter(Context context, int resource, List<String> list) {

            super(context, resource, list);

            // TODO Auto-generated constructor stub

            myContext = context;

            newList = list;

            inflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            final ViewHolder holder;

            if (view == null) {

                holder = new ViewHolder();

                view = inflater.inflate(R.layout.listview_item, null);

                holder.tvSname = (TextView) view.findViewById(R.id.tvtext_item);

                view.setTag(holder);

            } else {

                holder = (ViewHolder) view.getTag();

            }

            holder.tvSname.setText(newList.get(position).toString());

            return view;

        }

    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }
    */

}
