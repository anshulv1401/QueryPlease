package com.example.anshulvanawat.askrat2.main_activities;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.anshulvanawat.askrat2.other_actitvities.AskQuestion;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;
import com.example.anshulvanawat.askrat2.starting_activities.LoginActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String ID="id";

    public static final String ANDROID="android";
    public static final String JAVA="java";
    public static final String PHP="php";
    public static final String ADV_JAVA="adv_java";
    public static final String ORACLE="oracle";
    public static final String MYSQL="mysql";

    public static final String RATID = "ratid";
    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String EMAIL = "email";
    public static final String CONTACTNO = "contact";
    public static final String DATEOFBIRTH = "dateofbirth";
    public static final String GENDER = "gender";
    public static final String PASSWORD = "password";

    public static final String STUDENT_NAME = "studentname";
    public static final String SUBJECT = "subject";
    public static final String TOPIC = "topic";
    public static final String QUESTION = "question";
    public static final String DESCRIPTION = "description";
    public static final String NO_OF_VISITORS = "noofvisitors";
    public static final String ANSWERED = "answered";
    public static final String IMGAGE_PATH="imagepath";
    public static final String DATEINMILLIS = "dateinmillis";

    public static final String QUESTION_ID ="questionid";
    public static final String ANSWER="answer";
    public static final String STATUS="status";//tells whether the answer is ticked by the admin
    public static final String NO_OF_CHECK="noofcheck";//tells no. of votes

    public static final String CHECK="check";//used in php code to check that entry is creating or updating,true for
                                            // creating and false for updating
    public static final String SELECT_TOPIC = "--select_topic--";
    public static final String All = "--All--";
    public static final String SELECT_SUBJECT= "--select_subject--";
    public static final String OTHER = "other";

    public static final String SUBJECT_FRAGMENT = "subject_fragment";//ie question list fragment
    public static final String HOME_FRAGMENT = "home";
    public static final String PROFILE_FRAGMENT = "profile_fragment";
    public static final String SEARCH_FRAGMENT = "search_fragment";
    public static final String MY_QUESTION_FRAGMENT = "my_question_fragment";

   // public static final String MY_QUESTIONS_ANSWER = "my_questions_answer";//used as key of boolean extra to check that the person is
                                                    //check his own questions
    public static final String NOT_SUBJECT_BUT_RATID = "not_subject_but_ratid";

    public static final String QUESTION_LIST_FRAGMENT_TAG = "questionListFragment";
    public static final String PROFILE_FRAGMENT_TAG = "profileFragment";
    public static final String SEARCH_FRAGMENT_TAG = "SearchFragment";
    public static final String MY_QUESTION_LIST_FRAGMENT_TAG = "myQuestionListFragment";
    public static final String HOME_FRAGMENT_TAG = "homeFragment";

    public static final String HELP = "Help";
    public static final String FEEDBACK = "Feedback";
    public static final String LOGOUT = "Logout";
    public static final String ABOUTUS = "About Us";

    public static final String TITLEASKRAT="AskRAT";
    public static final String TITLEPROFILE="My Profile";
    public static final String TITLEMYQUESTION="My Question";
    public static final String TITLESEARCH="My Question";
    public static final String TITLEANDROID="Android";
    public static final String TITLEJAVA="Java";
    public static final String TITLEORACLE="Oracle";
    public static final String TITLEPHP="Php";
    public static final String TITLEADV_JAVA="Adv_java";
    public static final String TITLEMYSQL="MySql";
    public static final String TITLEFEEDBACK="Feedback";
    public static final String TITLEABOUTUS="About Us";
    public static final String TITLEHELP="Help";



    public static String CURRENT_FRAGMENT;

    //this array is used to store previous fragment when switching to other fragment, so that when back button is pressed the action bar
    // can be restored accordingly
    public static ArrayList<String> PREVIOUS_FRAGMENT_LIST=new ArrayList<>();

    public static String[] allSubjects;

    public static int year, month, day;
    Spinner topic_spinner;
    String subject_topics[];
    WebConnector2 wc;

    String menuTitle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CURRENT_FRAGMENT = HOME_FRAGMENT;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Snackbar snackbar=Snackbar.make(view, "Post Question?", Snackbar.LENGTH_LONG);
                snackbar.setAction("yes!!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(getApplication(),AskQuestion.class);
                                startActivity(intent);//refreshing fragment in onResume function

                            }
                        });
                snackbar.setActionTextColor(Color.GREEN);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        wc=new WebConnector2();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu m = navigationView.getMenu();
        SubMenu topChannelMenu = m.addSubMenu("Subjects");

        final String[][] subject=wc.getSubjects();//getting all subjects from db

        final int noOfSubject=subject.length;

        allSubjects=new String[noOfSubject+1];
        allSubjects[0]=SELECT_SUBJECT;

        for(int i=0;i<noOfSubject;i++) {
            //subject[i][0] is for id of subject
            if((subject[i][1]).equalsIgnoreCase("android")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.ic_action_name);
            }else if((subject[i][1]).equalsIgnoreCase("java")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.java);
            }else if((subject[i][1]).equalsIgnoreCase("oracle")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.oracle);
            }else if((subject[i][1]).equalsIgnoreCase("php")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.php);
            }else if((subject[i][1]).equalsIgnoreCase("adv_java")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.jsp);
            }else if((subject[i][1]).equalsIgnoreCase("mysql")) {
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.mysql);
            }else{
                topChannelMenu.add(subject[i][1]).setIcon(R.drawable.rat);
            }
            allSubjects[i+1]=subject[i][1];
        }


        MenuItem mi = m.getItem(m.size()-1);
        setTitle(mi.getTitle());


        //use the list from data base and set the subjects
        //use the path of the image from the server to get the bitmap and set it to the icon form data base
        //.setIcon(new BitmapDrawable(getResources(), myBitmap));

        navigationView.setNavigationItemSelectedListener(this);

//set fragment initially
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        HomeFragment homeFragment=new HomeFragment();
        setTitle(TITLEASKRAT);
        fragmentTransaction.replace(R.id.fragment_main,homeFragment,HOME_FRAGMENT_TAG);//.addToBackStack(HOME_FRAGMENT_TAG);;
        fragmentTransaction.commit();
    }




    @Override
    protected void onResumeFragments() {


        //refeshing fragment eachtime it get resumed
        FragmentManager fm=getSupportFragmentManager();

        Fragment frg = fm.findFragmentById(R.id.fragment_main);

        FragmentTransaction ft = fm.beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          FragmentManager fm = getSupportFragmentManager();
            int count=fm.getBackStackEntryCount();
            if (count > 0) {
                //setting up backstack entry for action bar title
                if(!PREVIOUS_FRAGMENT_LIST.isEmpty()) {
                    String fragmentName = PREVIOUS_FRAGMENT_LIST.get(PREVIOUS_FRAGMENT_LIST.size() - 1);
                    if (fragmentName.equalsIgnoreCase(HOME_FRAGMENT)) {
                        setTitle(TITLEASKRAT);
                        CURRENT_FRAGMENT = HOME_FRAGMENT;
                    } else if (fragmentName.equalsIgnoreCase(PROFILE_FRAGMENT)) {
                        setTitle(TITLEPROFILE);
                        CURRENT_FRAGMENT = PROFILE_FRAGMENT;
                    } else {
                        Toast.makeText(getApplicationContext(), "debug : This is the name of the fragment " + fragmentName, Toast.LENGTH_SHORT);
                    }
                    PREVIOUS_FRAGMENT_LIST.remove(PREVIOUS_FRAGMENT_LIST.size() - 1);

                    invalidateOptionsMenu();
                }
                fm.popBackStackImmediate();
            } else{
                final Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom_dialog3);

                TextView positiveView=(TextView) dialog.findViewById(R.id.positiveResponseExit);
                TextView nagativeView=(TextView) dialog.findViewById(R.id.nagativeResponseExit);

                positiveView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.super.onBackPressed();
                        dialog.dismiss();
                    }
                });

                nagativeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //do nothing
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
           //super.onBackPressed();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.clear();



        if (CURRENT_FRAGMENT==SUBJECT_FRAGMENT)  {

            getMenuInflater().inflate(R.menu.topic_menu, menu);
            MenuItem item = menu.findItem(R.id.action_topic_list);
            topic_spinner= (Spinner) MenuItemCompat.getActionView(item);
            ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.spinner_layout2, subject_topics);;

            topic_spinner.setAdapter(adapter);
            topic_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String topic=adapterView.getItemAtPosition(position).toString();
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                    QuestionListFragment questionListFragment=new QuestionListFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString(SUBJECT,menuTitle);
                    bundle.putString(TOPIC,topic);

                    questionListFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_main, questionListFragment,QUESTION_LIST_FRAGMENT_TAG);
                    fragmentTransaction.commit();


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if(CURRENT_FRAGMENT==PROFILE_FRAGMENT){
            getMenuInflater().inflate(R.menu.profile_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            //
            PREVIOUS_FRAGMENT_LIST.add(CURRENT_FRAGMENT);

            CURRENT_FRAGMENT=PROFILE_FRAGMENT;
            refreshActionBarMenu(this);//user defined method
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            ProfileFragment profileFragment = new ProfileFragment();
            setTitle(TITLEPROFILE);
            fragmentTransaction.replace(R.id.fragment_main, profileFragment, PROFILE_FRAGMENT_TAG).addToBackStack(PROFILE_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        else if(id==R.id.action_search){

            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            SearchFragment searchFragment=new SearchFragment();
            setTitle(TITLESEARCH);
            fragmentTransaction.replace(R.id.fragment_main, searchFragment, SEARCH_FRAGMENT_TAG);//.addToBackStack(SEARCH_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }else if(id==R.id.logout){

            final Dialog dialog=new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog2);

            TextView positiveView=(TextView) dialog.findViewById(R.id.positiveResponse);
            TextView nagativeView=(TextView) dialog.findViewById(R.id.nagativeResponse);

            positiveView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(MainActivity.RATID, "");
                    editor.putString(MainActivity.FIRST_NAME, "");
                    //email address is not set to "", so that we can fill the previous email
                    editor.putString(MainActivity.LAST_NAME, "");
                    editor.putString(MainActivity.PASSWORD, "");
                    editor.putString(MainActivity.DATEOFBIRTH, "");
                    editor.putString(MainActivity.CONTACTNO, "");
                    editor.putString(MainActivity.GENDER, "");
                    editor.putString(MainActivity.DATEINMILLIS, "");
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            nagativeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();


        int id = item.getItemId();

        menuTitle=item.getTitle().toString();
       // menuTitle = menuTitle.substring(0, 1).toUpperCase() + menuTitle.substring(1);//to capitalize first letter of string
        //no need


        if(id!=0) {
            if (id == R.id.nav_home) {
                HomeFragment homeFragment = new HomeFragment();
                setTitle(TITLEASKRAT);
                fragmentTransaction.replace(R.id.fragment_main, homeFragment, HOME_FRAGMENT_TAG);//.addToBackStack(HOME_FRAGMENT_TAG);
                fragmentTransaction.commit();

                CURRENT_FRAGMENT=HOME_FRAGMENT;
                refreshActionBarMenu(this);//user defined method

            }
        }else{

            if(menuTitle.equalsIgnoreCase(HELP)){

            }else if(menuTitle.equalsIgnoreCase(ABOUTUS)){

            }else if(menuTitle.equalsIgnoreCase(FEEDBACK)){

            }else {
                CURRENT_FRAGMENT = SUBJECT_FRAGMENT;
                subject_topics = wc.getTopics(menuTitle);

                refreshActionBarMenu(this);//user defined method

                QuestionListFragment questionListFragment = new QuestionListFragment();

                Bundle bundle = new Bundle();
                bundle.putString(SUBJECT, menuTitle);
                bundle.putString(TOPIC, SELECT_TOPIC);//to get all the questions
                questionListFragment.setArguments(bundle);
                setTitle(menuTitle);


                fragmentTransaction.replace(R.id.fragment_main, questionListFragment, QUESTION_LIST_FRAGMENT_TAG);//.addToBackStack(QUESTION_LIST_FRAGMENT_TAG);
                fragmentTransaction.commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            return new DatePickerDialog(this, myDateListener, 2016, 8, 11);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            year=arg1;
            month=arg2;
            day=arg3;

            ProfileFragment.showDate(year,month+1,day);
        }
    };


    static void refreshActionBarMenu(Activity activity)
    {
        activity.invalidateOptionsMenu();
    }




}



