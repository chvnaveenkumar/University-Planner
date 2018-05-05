package com.example.s530742.universityplanneradmin.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by S530474 on 4/10/2018.
 */

public class deleteClass {


    public void deleteFunction(String scoreKey,String uniKey,String state_id,String newCourse,String universtiyName,String courseNames)
    {
        DatabaseReference dbUniversity, dbScore;
        dbUniversity = FirebaseDatabase.getInstance().getReference("university");
        dbScore = FirebaseDatabase.getInstance().getReference("score");


        System.out.println(scoreKey+"scorekey");

        System.out.println(uniKey+" "+state_id+" "+newCourse+"  ");
        dbScore.child(scoreKey).removeValue();

        if(courseNames.split(",").length == 2)
        {
            dbUniversity.child(uniKey).removeValue();
        }else
        {
            System.out.println("check4");
            String uniID2 = uniKey;
            System.out.println("check5");
            University uni = new University(state_id, universtiyName, newCourse);
            System.out.println("check6");
            dbUniversity.child(uniID2).setValue(uni);
            System.out.println("check7");
            System.out.println("***************************");
        }

    }
}
