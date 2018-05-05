package com.example.s530742.universityplanneradmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
//    intitializing on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;
// setting the button from the xml
        Button signIn = (Button) findViewById(R.id.sign_in);
//setting onclick listener and intent to move to the next activity
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {
                Intent viewIntent = new Intent(context, LoginActivity.class);
                startActivity(viewIntent);
            }
        });

    }

}
