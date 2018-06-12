package com.example.anshulvanawat.askrat2.main_activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anshulvanawat.askrat2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class QuesAdapter extends ArrayAdapter<Que>{

    public QuesAdapter(Context context, ArrayList<Que> objects) {
        super(context, 0, objects);
    }

    public static class ViewHolder{

        TextView queView;
        TextView topicView;
        TextView descriptionView;
        TextView dateView;
        ImageView statusView;
        ImageView subjectIconView;

    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent){

        //get data for this position
        Que que=getItem(position);

        ViewHolder viewHolder;

        // this tech is recommended by google
        //ie. if View is lost then only we should create a new View, else reuse the
        //old one
        if(convertView==null){

            viewHolder=new ViewHolder();

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_question_list,parent,false);
            viewHolder.queView=(TextView)convertView.findViewById(R.id.QuestionText);
            viewHolder.topicView=(TextView)convertView.findViewById(R.id.listOueTopic);
            viewHolder.descriptionView =(TextView)convertView.findViewById(R.id.listQueDescription);
            viewHolder.dateView=(TextView)convertView.findViewById(R.id.listQueDate);
            viewHolder.statusView=(ImageView)convertView.findViewById(R.id.listQueStatusImg);
            viewHolder.subjectIconView=(ImageView)convertView.findViewById(R.id.listQueSubjectImg);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date=formatter.format(que.getDateinMilli());

        viewHolder.queView.setText(que.getQue());
        viewHolder.topicView.setText(que.getTopic());
        viewHolder.descriptionView.setText(que.getDescription());
        viewHolder.dateView.setText(date);
        viewHolder.statusView.setImageResource(que.getAssociateStatusDrawable());
        viewHolder.subjectIconView.setImageResource(que.getAssociateSubjectDrawable());
        return convertView;


    }

}
