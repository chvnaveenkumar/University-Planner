package com.example.s530742.universityplanneradmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.s530742.universityplanneradmin.model.Score;
import com.example.s530742.universityplanneradmin.model.State;
import com.example.s530742.universityplanneradmin.model.University;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_modify_uni_details extends AppCompatActivity {

    private String[] delete_state,delete_university,delete_course,delete_courselist;
    private String state_Name,university_Name;
    Spinner spinner_state,spinner_university,spinner_course;
    ArrayAdapter<String> adapter_university,adapter_course;
    Button deleteButton;
    DatabaseReference databaseState, databaseUniversity, databaseScore;
    List<State> stateItemList;
    String state_id;
    boolean stateStatus2 = false;
    Button updateButton;
    EditText greEditText,toeflEditText,ieltsEditText;
    String scoreId,courseName,uniName;
// on create method and setting all the elements from the xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_uni_details);

        final Context context = this;
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_university = (Spinner) findViewById(R.id.spinner_university);
        spinner_course = (Spinner) findViewById(R.id.spinner_course);
        updateButton =(Button) findViewById(R.id.updateButton);
        greEditText = (EditText) findViewById(R.id.greEditText);
        toeflEditText = (EditText) findViewById(R.id.toeflEditText);
        ieltsEditText = (EditText) findViewById(R.id.ieltsEditText);
        updateButton.setEnabled(false);

        databaseState = FirebaseDatabase.getInstance().getReference("state");
        databaseUniversity = FirebaseDatabase.getInstance().getReference("university");
        databaseScore = FirebaseDatabase.getInstance().getReference("score");
        stateItemList = new ArrayList<>();
//adding value to the event listener
        databaseState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stateItemList.clear();
                for (DataSnapshot stateSnapshot : dataSnapshot.getChildren()) {
                    State state = stateSnapshot.getValue(State.class);
                    stateItemList.add(state);
                }

                int stateLength = stateItemList.size();
                int i = 0;
                delete_state = new String[stateLength];
                for (State s : stateItemList) {
                    if (i == 0) {
                        delete_state[i] = s.getStateName();
                        i = i + 1;
                    } else {
                        delete_state[i] = s.getStateName();
                        i = i + 1;
                    }
                }
//setting array adapter of string type and setting values to populate the data onto the screen
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_state);
                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_state.setAdapter(adapter_state);
//setting spinner values with the adapter view on item seleted
                spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        spinner_university.setEnabled(true);
                        state_Name = parent.getItemAtPosition(position).toString();

                        for (State s : stateItemList) {
                            if (s.getStateName().equals(state_Name)) {
                                state_id = s.getStateID();
                                stateStatus2 = true;
                                break;
                            }
                        }
                        if(stateStatus2) {
//setting list of university type to a new arraylist
                            final List<University> universityItemList = new ArrayList<>();
                            databaseUniversity.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                                        University university = universitySnapshot.getValue(University.class);
                                        if (state_id.equals(university.getStateID()))
                                            universityItemList.add(university);
                                    }
                                    int universityLength = universityItemList.size();
                                    if(universityItemList.size() != 1) {
                                            int i = 0;
                                            delete_university = new String[universityLength-1];
                                            delete_courselist = new String[universityLength-1];
                                            System.out.println(universityLength+"   ");
                                            for (int j=0;j<universityItemList.size();j++) {
                                                if (i == 0) {
                                                    if(!universityItemList.get(j).getUniversityName().equals("New University")) {
                                                        delete_university[i] = universityItemList.get(j).getUniversityName();
                                                        delete_courselist[i] = universityItemList.get(j).getCourseNames();
                                                        i = i + 1;
                                                    }
                                                } else {
                                                    if(!universityItemList.get(j).getUniversityName().equals("New University")) {

                                                        delete_university[i] = universityItemList.get(j).getUniversityName();
                                                        delete_courselist[i] = universityItemList.get(j).getCourseNames();
                                                        i = i + 1;
                                                    }
                                                }
                                            }

                                    }else
                                    {
                                        delete_university = new String[1];
                                        delete_university[0] = "No Universitites";
                                        delete_courselist = new String[1];
                                        delete_courselist[0] = "No Courses";
                                    }

                                    adapter_university = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_university);
                                    adapter_university.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_university.setAdapter(adapter_university);
//spinner of the unviersity and setting on item seleted listener
                                    spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            university_Name = parent.getItemAtPosition(position).toString();
                                            spinner_course.setEnabled(true);
                                            String[] delete_course2 = new String[0];
                                            if(!delete_university[0].equals("No Universitites")) {
//traversing to all the deleted universty length list
                                                for (int l = 0; l < delete_university.length; l++) {
                                                    if (university_Name.equals(delete_university[l])) {
                                                        String[] create_course_temp = delete_courselist[l].split(",");
                                                        delete_course = new String[create_course_temp.length-1];
                                                        delete_course2 = delete_courselist[l].split(",");
                                                    }
                                                }
                                                int t=0;
                                                delete_course = new String[delete_course2.length-1];
//traversing the deleted course list for every element
                                                for(int p=0;p<delete_course2.length;p++)
                                                {
                                                    if(!delete_course2[p].equals("New Course")) {
                                                        delete_course[t] = delete_course2[p];
                                                        t++;
                                                    }
                                                }
                                                System.out.println(delete_course.length+"  "+delete_course2.length);
                                                updateButton.setEnabled(true);
                                            }else
                                            {
                                                delete_course = new String[1];
                                                delete_course[0] = "No Courses";
                                                updateButton.setEnabled(false);
                                            }
                                            adapter_course = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_course);
                                            adapter_course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner_course.setAdapter(adapter_course);
                                        }
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void search(View v)
    {
        System.out.println("inside search");

        databaseScore.addValueEventListener(new ValueEventListener() {
//List of Score type initializing to a new Array List
           List<Score> scoreItemList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scoreItemList.clear();
                courseName = spinner_course.getSelectedItem().toString();
                uniName = spinner_university.getSelectedItem().toString();
// Travesing to all the child elements of the data snapshot
                for (DataSnapshot stateSnapshot : dataSnapshot.getChildren()) {
                    Score score = stateSnapshot.getValue(Score.class);
                    if(score.getCourseID().equals(courseName) && score.getUniverstiyID().equals(uniName))
                    {
                            scoreId = stateSnapshot.getKey();
                        greEditText.setText(score.getGre()+"");
                        toeflEditText.setText(score.getTofel()+"");
                        ieltsEditText.setText(score.getIelts()+"");
                        updateButton.setEnabled(true);
                    }
                    stateSnapshot.getKey();
                    scoreItemList.add(score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

//method to update the data
        public void updateButton(View arg) {
            boolean status = false;
            Context context = this;
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            if(greEditText.getText().toString().isEmpty() || ieltsEditText.getText().toString().isEmpty() || toeflEditText.getText().toString().isEmpty())
            {
                alertDialog.setTitle("Whoops!!");
                alertDialog.setMessage(" Gre, Toefl and Ielts fields should not be empty!!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }else if(Integer.parseInt(greEditText.getText().toString()) == 0 || Double.parseDouble(ieltsEditText.getText().toString()) == 0 || Integer.parseInt(toeflEditText.getText().toString()) == 0)
            {
                alertDialog.setTitle("Whoops!!");
                alertDialog.setMessage(" Gre, Toefl and Ielts fields should not be '0'!!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
            else {

                String scoreID = scoreId;
                Score score = new Score(uniName,courseName,Integer.parseInt(greEditText.getText().toString()),Integer.parseInt(toeflEditText.getText().toString()),Double.parseDouble(ieltsEditText.getText().toString()));
                databaseScore.child(scoreID).setValue(score);


                alertDialog.setTitle("Message!!");
                alertDialog.setMessage("Modified the university detatils successfully !!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }
    public void signout(View v)
    {
        finish();
        Intent viewIntent = new Intent(this,LoginActivity.class);
    }
}
