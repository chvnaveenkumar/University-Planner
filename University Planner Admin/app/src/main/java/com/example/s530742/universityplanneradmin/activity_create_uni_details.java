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

public class activity_create_uni_details extends AppCompatActivity {

    private String[] create_state, create_university, create_courselist,create_course;
    private String state_Name, university_Name, course_Name;
    Spinner spinner_state, spinner_university, spinner_course;
    ArrayAdapter<String> adapter_university, adapter_course;
    EditText newUniversity, courseNew, greEditText, toeflEditText, ieltsEditText;
    DatabaseReference databaseState, databaseUniversity, databaseCourses, databaseScores;
    List<State> stateItemList;
    String state_id;
    boolean stateStatus2 = false;
    Button saveButton;
    String uniKey, courseNames,newCourse = "";

//onCreate method to set content view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_uni_details);

        final Context context = this;
//        setting all the buttons and spinners with the key id values on the activity
        saveButton = (Button) findViewById(R.id.saveButton);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_university = (Spinner) findViewById(R.id.spinner_university);
        spinner_course = (Spinner) findViewById(R.id.spinner_course);
        newUniversity = (EditText) findViewById(R.id.newUnivetisty);
        courseNew = (EditText) findViewById(R.id.courseNew);
        greEditText = (EditText) findViewById(R.id.greEditText);
        toeflEditText = (EditText) findViewById(R.id.toefleditText);
        ieltsEditText = (EditText) findViewById(R.id.ieltsEditText);
        databaseState = FirebaseDatabase.getInstance().getReference("state");
        databaseUniversity = FirebaseDatabase.getInstance().getReference("university");
        databaseCourses = FirebaseDatabase.getInstance().getReference("course");
        databaseScores = FirebaseDatabase.getInstance().getReference("score");
        saveButton.setEnabled(false);
        spinner_university.setEnabled(false);
        spinner_course.setEnabled(false);
        newUniversity.setEnabled(false);
        courseNew.setEnabled(false);
//    intitializing the stateitem list to a new array list
        stateItemList = new ArrayList<>();


      /*  String stateID = databaseState.push().getKey();
        State state = new State(stateID,"Ohio");
        databaseState.child(stateID).setValue(state);

        String uniID = databaseUniversity.push().getKey();
        University university = new University("-L93dFWCv4tDmGJHl-6D","New University","New Course");
        databaseUniversity.child(uniID).setValue(university);

        String scoreID = databaseScores.push().getKey();
        Score score = new Score(uniID,"New Course",292,80,6);
        databaseScores.child(scoreID).setValue(score);

        String scoreID2 = databaseScores.push().getKey();
        Score score2 = new Score(uniID,"Information Systems",290,80,6);
        databaseScores.child(scoreID).setValue(score);
*/
        databaseState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stateItemList.clear();
//                data snapshot iteration through the child elements
                for (DataSnapshot stateSnapshot : dataSnapshot.getChildren()) {
                    State state = stateSnapshot.getValue(State.class);
                    stateItemList.add(state);
                }
// initializing statelength to size of the state item list
                int stateLength = stateItemList.size();
                int i = 0;
                create_state = new String[stateLength];
                for (State s : stateItemList) {
                    if (i == 0) {
                        create_state[i] = s.getStateName();
                        i = i + 1;
                    } else {
                        create_state[i] = s.getStateName();
                        i = i + 1;
                    }
                }
// ArrayAdapter of string type in adapter state
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, create_state);
                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_state.setAdapter(adapter_state);

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
//                            initializing university item list to a new array list
                            final List<University> universityItemList = new ArrayList<>();
                            databaseUniversity.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    System.out.println("create ");
//traversing through all the elements in data snapshort of children
                                    for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                                        University university = universitySnapshot.getValue(University.class);
                                        if (state_id.equals(university.getStateID()))
                                            universityItemList.add(university);
                                    }
//checking if the university item list is empty
                                    if(!universityItemList.isEmpty()) {
                                        int universityLength = universityItemList.size();
                                        int i = 0;
                                        create_university = new String[universityLength];
                                        create_courselist = new String[universityLength];
// traversing through univesity item list
                                        for (University u : universityItemList) {
                                            System.out.println("bbb    "+ u + "  "+u.getUniversityName());
                                            if (i == 0) {
                                                create_university[i] = u.getUniversityName();
                                                create_courselist[i] =  u.getCourseNames();
                                                i = i + 1;
                                            } else {
                                                create_university[i] = u.getUniversityName();
                                                create_courselist[i] = u.getCourseNames();
                                                i = i + 1;
                                            }
                                        }
                                        newUniversity.setEnabled(false);
                                        if(create_university[0].equals("New University"))
                                            newUniversity.setEnabled(true);
                                    }else
                                    {
//  create university to a new string of size 1
                                        create_university = new String[1];
                                        create_university[0] = "No Universitites";
                                        if(create_university[0].equals("No Universitites"))
                                        {
                                            newUniversity.setEnabled(true);
                                        }
                                    }
                                    adapter_university = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, create_university);
                                    adapter_university.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_university.setAdapter(adapter_university);

                                    spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            university_Name = parent.getItemAtPosition(position).toString();
                                            spinner_course.setEnabled(true);

                                            if(!create_university[0].equals("No Universitites")) {
                                                System.out.println(create_university.length);
                                                System.out.println(create_courselist.length);
                                                for (int l = 0; l < create_university.length; l++) {
                                                    if (university_Name.equals(create_university[l])) {
                                                        String[] create_course_temp = create_courselist[l].split(",");
                                                        create_course = new String [create_course_temp.length];
                                                        create_course = create_courselist[l].split(",");
                                                    }
                                                }
                                                if(spinner_university.getSelectedItem().toString().equals("New University"))
                                                    newUniversity.setEnabled(true);
                                                else
                                                    newUniversity.setEnabled(false);
                                            }
                                            else
                                            {
                                                create_course = new String[] {"No Courses"};
                                                courseNew.setEnabled(true);
                                                saveButton.setEnabled(true);
                                            }
                                            adapter_course = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, create_course);
                                            adapter_course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner_course.setAdapter(adapter_course);
                                        }
                                        public void onNothingSelected(AdapterView<?> parent) {
                                        }
                                    });
                                }
//
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

        spinner_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        if(parent.getItemAtPosition(position).toString().equals("New Course"))
                                                        {
                                                            saveButton.setEnabled(true);
                                                            courseNew.setEnabled(true);
                                                        }else
                                                        {
                                                            saveButton.setEnabled(false);
                                                            courseNew.setEnabled(false);
                                                        }
                                                    }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        setting onclick listerner to the save button and doing something
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {

                String state,course="",university="";
                int gre = 0,toefl =0;
                double ielts =0;
                boolean t = true;
                state = spinner_state.getSelectedItem().toString();
//checking the condition if the course is enabled
                if(courseNew.isEnabled())
                {
                    saveButton.setEnabled(true);
                    if(!courseNew.getText().toString().isEmpty())
                    course = courseNew.getText().toString();
                }else
                {
// setting the save button to false.
                    saveButton.setEnabled(false);
                }
                if(newUniversity.isEnabled())
                {
                    if(!newUniversity.getText().toString().isEmpty())
                    university = newUniversity.getText().toString();
                }else
                {
                    university = spinner_university.getSelectedItem().toString();
                }
                if(!greEditText.getText().toString().equals(""))
                    gre = Integer.parseInt(greEditText.getText().toString());
                if(!toeflEditText.getText().toString().equals(""))
                    toefl = Integer.parseInt(toeflEditText.getText().toString());
                if(!ieltsEditText.getText().toString().equals(""))
                    ielts = Double.parseDouble(ieltsEditText.getText().toString());
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                if(course.equals("") || university.equals("") || university.isEmpty() || course.isEmpty() || course.equals(null) || university.equals(null))
                {
                    alertDialog.setTitle("Message!!");
                    alertDialog.setMessage("Above fields should not be empty or null!!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }else if(gre == 0 || ielts == 0 || toefl == 0)
                {
                    alertDialog.setTitle("Whoops!!");
                    alertDialog.setMessage(" Gre, Toefl and Ielts fields should not be '0' or empty!!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {

                    if(newUniversity.isEnabled())
                    {
                        String uniID = databaseUniversity.push().getKey();
                        course = course+",New Course";
                        University uni = new University(state_id,university,course);
                        databaseUniversity.child(uniID).setValue(uni);

                        String scoreID = databaseScores.push().getKey();
                        Score score = new Score(university,courseNew.getText().toString(),gre,toefl,ielts);
                        databaseScores.child(scoreID).setValue(score);

                        alertDialog.setTitle("Message!!");
                        alertDialog.setMessage("Created the university detatils successfully !!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                       finish();
                                        startActivity(getIntent());
                                    }
                                });
                        alertDialog.show();
                    }else
                    {
                        databaseUniversity.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String uni_1 = spinner_university.getSelectedItem().toString();

                                for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                                    University university = universitySnapshot.getValue(University.class);
                                    System.out.println(spinner_university.getSelectedItem().toString()+"  unicheck "+university.getUniversityName());
                                    if (spinner_university.getSelectedItem().toString().equals(university.getUniversityName()))
                                    {
                                        courseNames = university.getCourseNames();
                                        uniKey = universitySnapshot.getKey();
                                        break;
                                    }
                                }

                                String[] oldCourseNames = courseNames.split(",");
                                String[] newCourseName = new String[oldCourseNames.length + 1];
                                for (int r = 0; r < oldCourseNames.length - 1; r++) {
                                    newCourseName[r] = oldCourseNames[r];
                                }
                                newCourseName[oldCourseNames.length - 1] = courseNew.getText().toString();
                                newCourseName[oldCourseNames.length] = "New Course";

                                for (String s : newCourseName) {
                                    newCourse += s + ",";
                                }
                                newCourse = newCourse.substring(0, newCourse.length() - 1);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        alertDialog.setTitle("Message!!");
                        alertDialog.setMessage("Created the university detatils successfully !!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        String uniID2 = uniKey;
                                        University uni = new University(state_id, spinner_university.getSelectedItem().toString(), newCourse);
                                        databaseUniversity.child(uniID2).setValue(uni);

                                        String scoreID = databaseScores.push().getKey();
                                        Score score = new Score(spinner_university.getSelectedItem().toString(), courseNew.getText().toString(), Integer.parseInt(greEditText.getText().toString()), Integer.parseInt(toeflEditText.getText().toString()), Double.parseDouble(ieltsEditText.getText().toString()));
                                        databaseScores.child(scoreID).setValue(score);
                                        dialog.dismiss();
                                        finish();
                                        startActivity(getIntent());
                                    }
                                });
                        alertDialog.show();
                           }
                }
            }
        });
    }
//signout functioality to move logout


    public void signout(View v)
    {

        finish();
        Intent viewIntent = new Intent(this,LoginActivity.class);

    }
}