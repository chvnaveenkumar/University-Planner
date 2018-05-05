package com.example.s530742.universityplanneradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s530742.universityplanneradmin.model.LoginItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private AutoCompleteTextView email;
    private EditText password;
    private String e_mail,pwd;
    private boolean loginStatus;
    DatabaseReference databaseLogin;
    List<LoginItem> loginItemList;

//setting oncreate method and setting some variables for the access.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        databaseLogin = FirebaseDatabase.getInstance().getReference("login");
        loginItemList = new ArrayList<>();
        loginStatus = false;
    }

//    setting the email sign in method to verify the values entered in the xml
    public void email_sign_in_button(View v)
    {
        e_mail = email.getText().toString();
        pwd = password.getText().toString();

        databaseLogin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loginItemList.clear();
                for(DataSnapshot loginSnapshot:dataSnapshot.getChildren()){
                        LoginItem loginItem = loginSnapshot.getValue(LoginItem.class);
                        loginItemList.add(loginItem);
                        }
                        for(LoginItem loginItem:loginItemList)
                        {
                            if(loginItem.getEmail().equals(e_mail) && loginItem.getPassword().equals(pwd))
                            {
                                    loginStatus = true;
                                    break;
                            }
                        }
//check the login status
                if(loginStatus)
                {
                    Intent viewIntent = new Intent(context, Basic_Activity.class);
                    startActivity(viewIntent);
                    finish();
                }else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Whoops!!");
                    alertDialog.setMessage("The username and password combination given do not match. Try Again?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    email.setText("");
                                    password.setText("");
                                }
                            });
                    alertDialog.show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
