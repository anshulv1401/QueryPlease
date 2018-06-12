package com.example.anshulvanawat.askrat2.main_activities;

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

import com.example.anshulvanawat.askrat2.answer_activities.AnswerListActivity;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

import java.util.ArrayList;


public class QuestionListFragment extends ListFragment {

    public QuestionListFragment() {

    }



    private ArrayList<Que> ques;
    private QuesAdapter quesAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        WebConnector2 wc=new WebConnector2();
        try {
            String subject=MainActivity.ANDROID;
            String topic=MainActivity.All;

            Bundle bundle=this.getArguments();

            if(bundle!=null){

                subject=bundle.getString(MainActivity.SUBJECT);
                topic=bundle.getString(MainActivity.TOPIC);


            }

            //if()
            ques=wc.retrieveQuestionsString(subject,topic);


        } catch (Exception e) {
            ques=new ArrayList<Que>();
            ques.add(new Que("","","","Error while getting data",""));
        }

        quesAdapter=new QuesAdapter(getActivity(), ques);

        setListAdapter(quesAdapter);

        registerForContextMenu(getListView());

    }


    @Override
    public void onListItemClick(ListView l, View v, int position,long id){
        super.onListItemClick(l,v,position,id);

        launchAnswerListActivity(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu,v,menuInfo);
        System.out.print("onCreatecontextMenu");

        MenuInflater menuInflater=getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        System.out.println("onContextItemSelected");
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int rowPosition=info.position;
        switch(item.getItemId()) {
            case R.id.open:
                launchAnswerListActivity(rowPosition);
                Toast.makeText(getActivity(),"answer opened",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.report:
                Toast.makeText(getActivity(),"Ques is Reported",Toast.LENGTH_LONG).show();
                return true ;
        }

        return super.onContextItemSelected(item);
    }

    private void launchAnswerListActivity(int position){

        Que que=(Que)getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), AnswerListActivity.class);
        intent.putExtra(MainActivity.RATID,que.getRatid());
       // intent.putExtra(MainActivity.MY_QUESTIONS_ANSWER,false);
        intent.putExtra(MainActivity.ANSWERED,que.getAnswered());
        intent.putExtra(MainActivity.QUESTION_ID, que.getQueIdString());
        intent.putExtra(MainActivity.TOPIC,que.getTopic());
        intent.putExtra(MainActivity.STUDENT_NAME,que.getStudentName());
        intent.putExtra(MainActivity.SUBJECT,que.getSubject());
        intent.putExtra(MainActivity.QUESTION,que.getQue());
        intent.putExtra(MainActivity.DESCRIPTION,que.getDescription());
        intent.putExtra(MainActivity.DATEINMILLIS,que.getDateinMilli());
        startActivity(intent);
    }

}
