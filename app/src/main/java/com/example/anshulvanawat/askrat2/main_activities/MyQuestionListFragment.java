package com.example.anshulvanawat.askrat2.main_activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;
import com.example.anshulvanawat.askrat2.answer_activities.AnswerListActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyQuestionListFragment extends ListFragment {



    private ArrayList<Que> ques;
    private QuesAdapter quesAdapter;
    String ratId;

    public MyQuestionListFragment() {
        // Required empty public constructor

    }


    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        WebConnector2 webConnector2=new WebConnector2();

        MainActivity.CURRENT_FRAGMENT=MainActivity.MY_QUESTION_FRAGMENT;//used to change the action bar option
        MainActivity.refreshActionBarMenu(getActivity());//user defined method

        try{
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
            ratId=preferences.getString(MainActivity.RATID,"0");
            Log.i("ratid",ratId);
            ques=webConnector2.retrieveQuestionsString(MainActivity.NOT_SUBJECT_BUT_RATID,ratId);
        }catch (Exception e){
            e.printStackTrace();
            ques=new ArrayList<Que>();
            ques.add(new Que("","","","Error while getting data",""));
        }

        quesAdapter =new QuesAdapter(getActivity(),ques);
        setListAdapter(quesAdapter);

        registerForContextMenu(getListView());




    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);

        launchAnswerListActivity(position);
    }

    private void launchAnswerListActivity(int position){

        Que que=(Que)getListAdapter().getItem(position);

        Intent intent = new Intent(getActivity(), AnswerListActivity.class);
        intent.putExtra(MainActivity.QUESTION_ID, que.getQueIdString());
        intent.putExtra(MainActivity.TOPIC,que.getTopic());
        intent.putExtra(MainActivity.STUDENT_NAME,que.getStudentName());
        intent.putExtra(MainActivity.SUBJECT,que.getSubject());
        intent.putExtra(MainActivity.QUESTION,que.getQue());
        intent.putExtra(MainActivity.DESCRIPTION,que.getDescription());
        intent.putExtra(MainActivity.DATEINMILLIS,que.getDateinMilli());

        startActivity(intent);
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

        Que que=(Que)getListAdapter().getItem(rowPosition);

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

}
