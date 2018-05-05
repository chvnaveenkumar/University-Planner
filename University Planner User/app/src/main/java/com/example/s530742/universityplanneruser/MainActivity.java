package com.example.s530742.universityplanneruser;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.s530742.universityplanneruser.model.Score;
import com.example.s530742.universityplanneruser.model.State;
import com.example.s530742.universityplanneruser.model.University;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//intitializing the variables needed
    private String[] delete_state,delete_university,delete_course,delete_courselist;
    DatabaseReference databaseState, databaseUniversity, databaseScore;
    List<State> stateItemList;
    String state_id;
    boolean stateStatus2 = false;

    String scoreId;

    private String state_Name,university_Name;
    Spinner spinner_state,spinner_university,spinner_course;
    ArrayAdapter<String> adapter_university,adapter_course;
    TextView greEditText,toeflEditText,ieltsEditText;
    Button search;
//on create method to initalize the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//getting the control over all the elements of the xml for the functionality
        final Context context = this;
        spinner_state = (Spinner) findViewById(R.id.statespinner);
        spinner_university = (Spinner) findViewById(R.id.universitySpinner);
        spinner_course = (Spinner) findViewById(R.id.courseSpinner);
        greEditText = (TextView) findViewById(R.id.editTextgre);
        toeflEditText = (TextView) findViewById(R.id.editTexttofel);
        ieltsEditText = (TextView) findViewById(R.id.editTextielts);
        search =(Button) findViewById(R.id.search);

        databaseState = FirebaseDatabase.getInstance().getReference("state");
        databaseUniversity = FirebaseDatabase.getInstance().getReference("university");
        databaseScore = FirebaseDatabase.getInstance().getReference("score");
        stateItemList = new ArrayList<>();
// setting listener to the database state to sense any kind of variations
        databaseState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//method used to set the data change
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
// Array Adapater of string type to set the values to the view
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, delete_state);
                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_state.setAdapter(adapter_state);
//setting spinner state to set on items selected listener
                spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        greEditText.setText("");
                        toeflEditText.setText("");
                        ieltsEditText.setText("");
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
                                    if(universityItemList.size() != 1) {

                                        int i = 0;
                                            delete_university = new String[universityLength-1];
                                            delete_courselist = new String[universityLength-1];
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
//    method used to search as per the value entered
    public void search(View v)
    {

        databaseScore.addValueEventListener(new ValueEventListener() {

            List<Score> scoreItemList = new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scoreItemList.clear();
                String courseName = spinner_course.getSelectedItem().toString();
                String uniName = spinner_university.getSelectedItem().toString();
                for (DataSnapshot stateSnapshot : dataSnapshot.getChildren()) {
                    Score score = stateSnapshot.getValue(Score.class);
                    if(score.getCourseID().equals(courseName) && score.getUniverstiyID().equals(uniName))
                    {
                        scoreId = stateSnapshot.getKey();
                        greEditText.setText(score.getGre()+"");
                        toeflEditText.setText(score.getTofel()+"");
                        ieltsEditText.setText(score.getIelts()+"");
                        break;
                    }else
                    {
                        greEditText.setText("");
                        toeflEditText.setText("");
                        ieltsEditText.setText("");
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
// method used to reset values on the xml
    public void reset(View v)
    {
        greEditText.setText("");
        ieltsEditText.setText("");
        toeflEditText.setText("");

    }
}
