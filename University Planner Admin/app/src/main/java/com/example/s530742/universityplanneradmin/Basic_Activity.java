package com.example.s530742.universityplanneradmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.s530742.universityplanneradmin.model.Score;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Basic_Activity extends AppCompatActivity {


// setting the oncreate value to initialize the activity and setting the butons and values from the xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_);
        final Context context = this;
//accessing the button from the xml
        Button create_uni_details = (Button) findViewById(R.id.create_uni_details);

             create_uni_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
               // finish();
                Intent viewIntent = new Intent(context, activity_create_uni_details.class);
                startActivity(viewIntent);
            }
        });
//accesing button modify uni details from the xml
        Button modify_uni_details = (Button) findViewById(R.id.modify_uni_details);
        modify_uni_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
                //finish();
                Intent viewIntent = new Intent(context, activity_modify_uni_details.class);
                startActivity(viewIntent);

            }
        });
//Accesing the delte button from the xml
        Button delete_uni_details = (Button) findViewById(R.id.delete_univ_details);
        delete_uni_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
                //finish();
                Intent viewIntent = new Intent(context, activity_delete_uni_details.class);
                startActivity(viewIntent);

            }
        });
    }
    public void signout(View v)
    {
            finish();
            Intent viewIntent = new Intent(this,LoginActivity.class);
    }
}
