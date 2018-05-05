package com.example.s530742.universityplanneradmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.s530742.universityplanneradmin.model.Score;
import com.example.s530742.universityplanneradmin.model.State;
import com.example.s530742.universityplanneradmin.model.University;
import com.example.s530742.universityplanneradmin.model.deleteClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class activity_delete_uni_details extends AppCompatActivity {

    private String[] delete_state,delete_university,delete_course,delete_courselist;
    private String state_Name,university_Name;
    Spinner spinner_state,spinner_university,spinner_course;
    ArrayAdapter<String> adapter_university,adapter_course;
    Button deleteButton,check;
    DatabaseReference databaseState, databaseUniversity, databaseScore;
    List<State> stateItemList;
    String state_id;
    boolean stateStatus2 = false;
    String uniKey,scoreKey, courseNames,newCourse = "";
    static boolean flag = true;

//setting on create method and getting the access to all the elements on xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_uni_details);

        final Context context = this;
        spinner_state = (Spinner) findViewById(R.id.spinner_del_state);
        spinner_university = (Spinner) findViewById(R.id.spinner_del_university);
        spinner_course = (Spinner) findViewById(R.id.spinner_del_course);
        check = (Button) findViewById(R.id.fetchButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        deleteButton.setEnabled(false);
        databaseState = FirebaseDatabase.getInstance().getReference("state");
        databaseUniversity = FirebaseDatabase.getInstance().getReference("university");
        databaseScore = FirebaseDatabase.getInstance().getReference("score");
        stateItemList = new ArrayList<>();
        activity_delete_uni_details.flag = true;
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
// setting a string array adapter adapter sate to a the layout simple spinner item
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_state);
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
                                    System.out.println("University Item list+ "+universityItemList.size());
                                    if(universityItemList.size() != 1) {
                                            int i = 0;
                                            delete_university = new String[universityLength-1];
                                            delete_courselist = new String[universityLength-1];
                                            for (int j=0;j<universityItemList.size();j++) {
                                                if (i == 0) {
                                                    if (!universityItemList.get(j).getUniversityName().equals("New University")) {

                                                        delete_university[i] = universityItemList.get(j).getUniversityName();
                                                        delete_courselist[i] = universityItemList.get(j).getCourseNames();
                                                        i = i + 1;
                                                    }
                                                } else {
                                                    if (!universityItemList.get(j).getUniversityName().equals("New University")) {
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

                                    spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            university_Name = parent.getItemAtPosition(position).toString();
                                            spinner_course.setEnabled(true);

                                            String[] delete_course2 = new String[0];
                                            if(!delete_university[0].equals("No Universitites")) {
                                                for (int l = 0; l < delete_university.length; l++) {
                                                    if (university_Name.equals(delete_university[l])) {
                                                        String[] create_course_temp = delete_courselist[l].split(",");
                                                        delete_course = new String[create_course_temp.length-1];
                                                        delete_course2 = delete_courselist[l].split(",");

                                                    }
                                                }
                                                int t=0;
                                                delete_course = new String[delete_course2.length-1];
                                                for(int p=0;p<delete_course2.length;p++)
                                                {
                                                    if(!delete_course2[p].equals("New Course")) {
                                                        delete_course[t] = delete_course2[p];
                                                        t++;
                                                    }
                                                }
                                            }else
                                            {
                                                delete_course = new String[1];
                                                delete_course[0] = "No Courses";
                                            }
                                            adapter_course = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_course);
                                            adapter_course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner_course.setAdapter(adapter_course);
                                            if(spinner_course.getSelectedItem().toString().equals("No Courses"))
                                            {
                                                check.setEnabled(false);
                                            }else
                                            {
                                                check.setEnabled(true);
                                            }
                     //                       activity_delete_uni_details.flag = true;
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
//fetch method to intitialize the database university and adding event listener
    public void fetch(View v)
    {
        databaseUniversity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(activity_delete_uni_details.flag) {
                    String uni_1 = spinner_university.getSelectedItem().toString();
                    for (DataSnapshot universitySnapshot : dataSnapshot.getChildren()) {
                        University university = universitySnapshot.getValue(University.class);
                        if (spinner_university.getSelectedItem().toString().equals(university.getUniversityName())) {
                            courseNames = university.getCourseNames();
                            uniKey = universitySnapshot.getKey();
                            break;
                        }
                    }
                    String[] oldCourseNames = courseNames.split(",");
                    String[] newCourseName = new String[oldCourseNames.length - 1];
                    int w = 0;
                    for (int r = 0; r < oldCourseNames.length; r++) {
                        if (!oldCourseNames[r].equals(spinner_course.getSelectedItem().toString())) {
                            newCourseName[w] = oldCourseNames[r];
                            w = w + 1;
                        }
                    }
                    for (String s : newCourseName) {
                        newCourse += s + ",";
                    }
                    newCourse = newCourse.substring(0, newCourse.length() - 1);

                    databaseScore.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("databaseScore ************************8");

                            String uni_2 = spinner_university.getSelectedItem().toString();
                            String cour_2 = spinner_course.getSelectedItem().toString();
                            for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                                Score score = scoreSnapshot.getValue(Score.class);
                                System.out.println(uni_2+" "+score.getUniverstiyID()+" "+cour_2+" "+score.getCourseID()+" "+uni_2.equals(score.getUniverstiyID())+" "+cour_2.equals(score.getCourseID()));
                                if (uni_2.equals(score.getUniverstiyID()) && cour_2.equals(score.getCourseID())) {
                                    scoreKey = scoreSnapshot.getKey();
                                    System.out.println(scoreKey+" score");
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(!spinner_course.getSelectedItem().toString().equals("No Courses"))
                    {
                        System.out.println(scoreKey+" "+uniKey);
                        deleteButton.setEnabled(true);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
//delete method used to delete the details from the database
    public void delete(View v)
    {
        System.out.println(scoreKey+"scorekey");
        String universtiyName = spinner_university.getSelectedItem().toString();
        System.out.println(uniKey+" "+state_id+" "+newCourse+"  ");
        deleteClass dc = new deleteClass();
        dc.deleteFunction(scoreKey,uniKey,state_id,newCourse,universtiyName,courseNames);
        System.out.println("After check button");

        activity_delete_uni_details.flag = false;
        finish();
        startActivity(getIntent());
    }

    public void signout(View v)
    {
        finish();
        Intent viewIntent = new Intent(this,LoginActivity.class);
    }
}
