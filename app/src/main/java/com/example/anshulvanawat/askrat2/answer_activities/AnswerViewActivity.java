package com.example.anshulvanawat.askrat2.answer_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.WebConnector2;
import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.main_activities.Que;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class AnswerViewActivity extends AppCompatActivity {


    TextView nameTextView;
    TextView answerTextView;
    TextView dateTextView;
    TextView positiveView;//option/
    TextView statusQuestionText;//question
    ImageView statusimgView;
    ImageView answerImageView;
    TextView notice;//to give notice to users other then the poster himself


    String nameStudent;
    String imagePath;
    long status;
    long dateinmillis;
    String answer;
    Boolean myQuestionsAnswer=false;

    String ratidOfPoster;
    String questionId;
    Long answered;
    String answerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_view);
        setTitle("Answer");
        final String ratidOfViewer;

        nameTextView=(TextView)findViewById(R.id.answerViewName);
        answerTextView=(TextView)findViewById(R.id.answerViewAnswer);
        dateTextView=(TextView)findViewById(R.id.answerViewDate);
        statusimgView=(ImageView) findViewById(R.id.answerViewStatus);
        answerImageView=(ImageView)findViewById(R.id.answerImage);
        positiveView=(TextView)findViewById(R.id.positiveAnswerView);
        statusQuestionText=(TextView)findViewById(R.id.status_question_text);
        notice=(TextView)findViewById(R.id.noticeText);

        Intent intent=getIntent();

        answerId=intent.getExtras().getLong(MainActivity.ID)+"";
        questionId=intent.getExtras().getString(MainActivity.QUESTION_ID);
        nameStudent=intent.getExtras().getString(MainActivity.STUDENT_NAME);
        answer=intent.getExtras().getString(MainActivity.ANSWER);
        imagePath=intent.getExtras().getString(MainActivity.IMGAGE_PATH);
        dateinmillis=intent.getExtras().getLong(MainActivity.DATEINMILLIS);
        status=intent.getExtras().getLong(MainActivity.STATUS);


        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        ratidOfViewer=sharedPreferences.getString(MainActivity.RATID,"NAN");


        final WebConnector2 wc=new WebConnector2();

        ArrayList<Que> que= null;
        try {
            que = wc.getQueById(questionId);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Iterator<Que> itr=que.iterator();
        Que que1=itr.next();//used to get ratid of the person who has posted the question
        //this id will be compared to the viewers id,if match found then the person will be able to see the option of
        // ticking the answer correct or incorrect
        //this is done so that the only the poster can get the option, not everyone




        ratidOfPoster=que1.getRatid();
        answered=que1.getAnswered();
        if(!ratidOfPoster.equals(ratidOfViewer) && status!=0){
            notice.setVisibility(View.VISIBLE);
        }
        if(status==1 ){
            statusimgView.setImageResource(R.drawable.check);
        }

        if(ratidOfPoster.equals(ratidOfViewer) && answered==0){

            positiveView.setVisibility(View.VISIBLE);
            statusQuestionText.setVisibility(View.VISIBLE);
            positiveView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String check="false";//for updating
                    Boolean questionUpdate =wc.postQuestion("","","",questionId,"","","","","",check);
                    Boolean answerUpdate=wc.postAnswer("","",answerId,"","",check);
                    if(questionUpdate!=true){
                        Toast.makeText(getApplication(),"question not updated",Toast.LENGTH_LONG).show();
                    }else if(answerUpdate!=true){
                        Toast.makeText(getApplication(),"answer not updated",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplication(),"Your choice is saved",Toast.LENGTH_LONG).show();
                        AnswerViewActivity.this.finish();//refreshing activity
                        AnswerViewActivity.this.startActivity(AnswerViewActivity.this.getIntent());
                    }
                }
            });

        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date=formatter.format(dateinmillis);
        nameTextView.setText(nameStudent);
        answerTextView.setText(answer);
        dateTextView.setText(date);



    }
}
