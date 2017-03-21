package com.example.parulchopra.firebase_app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
final static String DB_URL= "https://simple-50479.firebaseio.com/";

EditText name,desp;
    Button save;
Firebase fire;
    ListView lv;
    ArrayList<String> names = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
lv =(ListView) findViewById(R.id.lv);
        Firebase.setAndroidContext(this);
         fire = new Firebase(DB_URL);
         this.retreivedata();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                savebutton();
            }
        });
    }

    public void savebutton()
    {
        Dialog d = new Dialog(this);
        d.setTitle("save online");
        d.setContentView(R.layout.data);
        name= (EditText) d.findViewById(R.id.nameEditText);
        desp= (EditText) d.findViewById(R.id.despEditText);
        save= (Button) d.findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             adddata(name.getText().toString(),desp.getText().toString());
                name.setText("");
                desp.setText("");
            }
        });
        d.show();
    }

    public void adddata(String name,String desp)
    {
    Movie m =new Movie();
        m.setName(name);
        m.setDescription(desp);
        fire.child("Movie").push().setValue(m);

    }


     public  void retreivedata()
     {
         fire.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              getupdates(dataSnapshot);
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                 getupdates(dataSnapshot);
             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(FirebaseError firebaseError) {

             }
         });
     }
    public void getupdates( DataSnapshot ds)
    {
        names.clear();
        for (DataSnapshot data : ds.getChildren())
        {
            Movie m = new Movie();
            m.setName((data.getValue(Movie.class).getName()));
            names.add(m.getName());
            if (names.size()> 0)
            {
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,names);
                lv.setAdapter(adapter);
            }
            else{
                Toast.makeText(MainActivity.this,"No Data",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
