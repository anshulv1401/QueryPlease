package com.example.anshulvanawat.askrat2.answer_activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anshulvanawat.askrat2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by anshul vanawat on 7/30/2016.
 */

public class AnsAdapter extends ArrayAdapter<Ans> {

    public AnsAdapter(Context context, ArrayList<Ans> objects) {
        super(context,0, objects);
    }
    /*

    public static class ViewHolder{

        TextView studentView;
        TextView descriptionView;
        TextView dateView;
        ImageView statusView;

    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent){

        //get data for this position
        Ans ans=getItem(position);

        ViewHolder viewHolder;

        // this tech is recommended by google
        //ie. if View is lost then only we should create a new View, else reuse the
        //old one
        if(convertView==null){

            viewHolder=new ViewHolder();

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_answer_list,parent,false);
            viewHolder.studentView=(TextView)convertView.findViewById(R.id.StudentName);
            viewHolder.descriptionView =(TextView)convertView.findViewById(R.id.listAnsDescription);
            viewHolder.dateView=(TextView)convertView.findViewById(R.id.listAnsDate);
            viewHolder.statusView=(ImageView)convertView.findViewById(R.id.listAnsStatusImg);


            convertView.setTag(viewHolder);

        }else{
           viewHolder=(ViewHolder)convertView.getTag();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date=formatter.format(ans.getDateCreatedMilli());

        viewHolder.studentView.setText(ans.getStudentName());
        viewHolder.descriptionView.setText(ans.getAnswer());
        viewHolder.dateView.setText(date);

        if(ans.getStatus()==1){
            statusView.setImageResource(R.drawable.check);
        }else{
            statusView.setVisibility(View.GONE);
        }

        return convertView;


    }

        */

    TextView studentView;
    TextView descriptionView;
    TextView dateView;
    ImageView statusView;

    @Override
    public View getView(int position,View convertView,ViewGroup parent){

        Ans ans=getItem(position);


        convertView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_answer_list,parent,false);
        studentView=(TextView)convertView.findViewById(R.id.StudentName);
        descriptionView =(TextView)convertView.findViewById(R.id.listAnsDescription);
        dateView=(TextView)convertView.findViewById(R.id.listAnsDate);
        statusView=(ImageView)convertView.findViewById(R.id.listAnsStatusImg);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date=formatter.format(ans.getDateCreatedMilli());

        studentView.setText(ans.getStudentName());
        descriptionView.setText(ans.getAnswer());
        dateView.setText(date);
        if(ans.getStatus()==1){
            statusView.setImageResource(R.drawable.check);
        }else{
            statusView.setVisibility(View.GONE);
        }

        return convertView;


    }

}
