# University Planner #

Author: Naveen Kumar Chandaluri

## What is this repository for? ##

The “University Planner” is a native android application that brings change to the conventional method of
searching Universities and their requirements manually. This application reduces lot of time and workload 
by providing the details of the Universities by just selecting the state which gives the information of all
the Universities in that state, its offered courses and their requirements. It provides multiple user
accessibility and also has different user privileges. This system uses centralized databases, which provides
easy retrieval of data.

### Administrator ###
Admin is the main authority of this site, so he has all the privileges. This homepage has two options of whether it is an existing or a new university.

The administrator can perform following functions:

--- He can add details of the Universities and their courses.

--- He can delete the details of the Universities and their courses.

--- He can update the details of the Universities and their courses.

### User ###

--- User does not need to register to get started with the application.

--- After user launch the application, he is provided with selection tab where he can search for the Universities and their requirements based on the state he entered. The selection has a dropdown menu which contains the list of states. 

•	As the user selects the state he is provided with a list of all the universities in that state.

•	As the user selects a particular University, he is provided with a description of all the courses it offers.

•	It also provides information about the requirements of the University like minimum GRE score, IELTS/TOEFL score, etc.

## How do I get set up? ##

## Installation ##
Clone the repository and import into Android Studio.

## Test credentials- ##
Admin username: admin@gmail.com
Password: admin123

## Supported devices: ##
google nexus 5 – 4.4.4 – API 19 – 1080X1920

## Database: ##
There is a common database for both the applications i.e Google FireBase. 
For admin: The details of all the universities are stored in the database using the administrator application.
For user: The user can retrieve the details stored in the database by the administrator.

## Sequence information ##
1.	Open Android Studio and launch the Android SDK manager from it
2.	Select Open an existing Android Studio project
3.	Choose the project directory.
4.	Run the application.