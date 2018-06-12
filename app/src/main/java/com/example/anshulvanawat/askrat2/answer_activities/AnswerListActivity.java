package com.example.anshulvanawat.askrat2.answer_activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.anshulvanawat.askrat2.other_actitvities.GiveAnswer;
import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.R;

import java.text.SimpleDateFormat;

public class AnswerListActivity extends AppCompatActivity {


    String questionid;
    String ratid;
    String topic;
    String studentName;
    String subject;
    String question;
    String description;
    long dateinmillis;



    TextView studentNametv;
    TextView topictv;
    TextView questiontv;
    TextView descriptiontv;
    TextView datetv;
    /**
     *decription about question
     * list of answers, answer list fragment
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        studentNametv=(TextView)findViewById(R.id.studentNameQue);
        topictv=(TextView)findViewById(R.id.topic);
        questiontv=(TextView)findViewById(R.id.questionText2);
        descriptiontv=(TextView)findViewById(R.id.descriptionText2);
        datetv=(TextView)findViewById(R.id.quedate);


        Intent intentPrev=getIntent();

        questionid= intentPrev.getExtras().getString(MainActivity.QUESTION_ID);
        topic= intentPrev.getExtras().getString(MainActivity.TOPIC);
        studentName= intentPrev.getExtras().getString(MainActivity.STUDENT_NAME);
        subject= intentPrev.getExtras().getString(MainActivity.SUBJECT);
        question= intentPrev.getExtras().getString(MainActivity.QUESTION);
        description= intentPrev.getExtras().getString(MainActivity.DESCRIPTION);
        dateinmillis= intentPrev.getExtras().getLong(MainActivity.DATEINMILLIS);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date=formatter.format(dateinmillis);

        studentNametv.setText(studentName);
        topictv.setText(topic);
        questiontv.setText(question);
        descriptiontv.setText(description);
        datetv.setText(date);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Snackbar snackbar=Snackbar.make(view, "Post your answer?", Snackbar.LENGTH_LONG);
                snackbar.setAction("yes!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                launchGiveAnswerActivity();
                            }
                        });
                snackbar.setActionTextColor(Color.GREEN);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        });
    }


    private void launchGiveAnswerActivity(){


        Intent intent = new Intent(AnswerListActivity.this, GiveAnswer.class);
        intent.putExtra(MainActivity.QUESTION_ID, questionid);
        intent.putExtra(MainActivity.TOPIC,topic);
        intent.putExtra(MainActivity.STUDENT_NAME,studentName);
        intent.putExtra(MainActivity.SUBJECT,subject);
        intent.putExtra(MainActivity.QUESTION,question);
        intent.putExtra(MainActivity.DESCRIPTION,description);
        intent.putExtra(MainActivity.DATEINMILLIS,dateinmillis);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //refreshing activity, restarting activity
        finish();
        startActivity(getIntent());

    }
}
