package com.example.s530742.universityplanneruser.model;

public class State {

    private String StateID;
    private String StateName;

    public State(){

    }

    public State(String stateID, String stateName) {
        StateID = stateID;
        StateName = stateName;
    }

    public String getStateID() {
        return StateID;
    }

    public void setStateID(String stateID) {
        StateID = stateID;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }
}
