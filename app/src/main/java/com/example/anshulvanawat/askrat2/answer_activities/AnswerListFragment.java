package com.example.anshulvanawat.askrat2.answer_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.main_activities.Que;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

import java.util.ArrayList;
import java.util.Iterator;


public class AnswerListFragment extends ListFragment {

    //list of answer

    public AnswerListFragment() {
        // Required empty public constructor
    }

    private ArrayList<Ans> ans;
    private AnsAdapter ansAdapter;
    Boolean myQuestionsAnswer;
    String ratid;
    String questionId;//explained later
    Long answered;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        WebConnector2 wc=new WebConnector2();
        try {

            Intent intent=getActivity().getIntent();
            ratid=intent.getExtras().getString(MainActivity.RATID);
            questionId=intent.getExtras().getString(MainActivity.QUESTION_ID);
            //myQuestionsAnswer=intent.getExtras().getBoolean(MainActivity.MY_QUESTIONS_ANSWER);
            answered=intent.getExtras().getLong(MainActivity.ANSWERED);//used to check weather the question is answered or not,
            //used for condition check same as que1's ratid is used
            ans=wc.retrieveAnswersString(questionId);


        } catch (Exception e) {
            ans=new ArrayList<Ans>();
            ans.add(new Ans("Error while getting data","","",""));
        }

        ansAdapter=new AnsAdapter(getActivity(), ans);

        setListAdapter(ansAdapter);
        registerForContextMenu(getListView());
        //set the array adapter here
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);

        launchAnswerViewActivity(position);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu,v,menuInfo);

        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int rowPosition=info.position;
        switch(item.getItemId()) {
            case R.id.open:
                launchAnswerViewActivity(rowPosition);
                Toast.makeText(getActivity(),"answer opened",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.report:
                Toast.makeText(getActivity(),"Answer is Reported",Toast.LENGTH_LONG).show();
                return true ;
        }

        return super.onContextItemSelected(item);


    }


    private void launchAnswerViewActivity(int position){

        Ans ans=(Ans)getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), AnswerViewActivity.class);

        intent.putExtra(MainActivity.ID,ans.getAnsId());
        intent.putExtra(MainActivity.RATID,ratid);
        intent.putExtra(MainActivity.QUESTION_ID,questionId);//getting ratid of the person who posted
        //intent.putExtra(MainActivity.MY_QUESTIONS_ANSWER,myQuestionsAnswer);
        intent.putExtra(MainActivity.ANSWERED,answered);
        intent.putExtra(MainActivity.STUDENT_NAME,ans.getStudentName());
        intent.putExtra(MainActivity.ANSWER,ans.getAnswer());
        intent.putExtra(MainActivity.DATEINMILLIS,ans.getDateCreatedMilli());
        intent.putExtra(MainActivity.STATUS,ans.getStatus());
        intent.putExtra(MainActivity.IMGAGE_PATH,ans.getImagepath());

        startActivity(intent);
    }



}
