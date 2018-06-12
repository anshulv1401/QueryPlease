package com.example.anshulvanawat.askrat2.main_activities;

import com.example.anshulvanawat.askrat2.R;

import java.util.Calendar;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Que {


    private String studentName,subject,topic,que,description,ratid,imagepath;
    private long queId,noOfVisitors,answered, dateinMilli;  //answered is used to store a no.,0 for not answered, 1 for answered



    public Que(String queId, String studentName, String subject, String topic, String que,String description,String ratid, String noOfVisitors,
               String answered,String imagepath ,String dateinMilli){

    this.queId=Long.parseLong(queId);
    this.studentName=studentName;
    this.subject=subject;
    this.topic=topic;
    this.que=que;
    this.description=description;
    this.ratid=ratid;
    this.noOfVisitors=Long.parseLong(noOfVisitors);
    this.answered=Long.parseLong(answered);//to check the status of que, answered or not
    this.imagepath="NAN";
    this.dateinMilli =Long.parseLong(dateinMilli);


    }

  /*  public Que(String studentName, String subject, String topic, String que,String description,String answered,String imagepath){

        this.queId=0;
        this.studentName=studentName;
        this.subject=subject;
        this.topic=topic;
        this.que=que;
        this.description=description;
        this.ratid=0;
        this.noOfVisitors=0;
        this.answered=Long.parseLong(answered);
        this.imagepath=imagepath;
        this.dateinMilli =Calendar.getInstance().getTimeInMillis();

    }*/
    public Que(String studentName, String subject, String topic, String que,String description){

        this.queId=0;
        this.studentName=studentName;
        this.subject=subject;
        this.topic=topic;
        this.que=que;
        this.description=description;
        this.ratid=0+"";
        this.noOfVisitors=0;
        this.answered=0;
        this.imagepath="NAN";
        this.dateinMilli = Calendar.getInstance().getTimeInMillis();

    }




    public String getStudentName(){return studentName;}
    public String getSubject(){return subject;}
    public String getTopic(){return topic;}
    public String getQue(){return que;}
    public String getDescription(){return description;}
    public long getQueId(){return queId;}
    public String getQueIdString(){return queId+"";}
    public String getRatid(){return ratid;}
    public long getNoOfVisitors(){return noOfVisitors;}
    public long getAnswered(){return answered;}
    public long getDateinMilli(){return dateinMilli;}

    public String toString(){
        return "ID: "+queId+" StudentName: "+ studentName+" Subject: "+subject+" topic: "
                +topic +" Que: "+que+ " queId: "+queId+" ratid: "+ratid+" no of visitors: "
                + noOfVisitors +" answered: "+answered+" dateinMilli: "+ dateinMilli;
    }

    public int getAssociateSubjectDrawable(){

        switch(subject.toLowerCase()){
            case MainActivity.ANDROID:
                return R.drawable.ic_action_name;
            case MainActivity.JAVA:
                return R.drawable.java;
            case MainActivity.ADV_JAVA:
                return R.drawable.jsp;
            case MainActivity.PHP:
                return R.drawable.php;
            case MainActivity.ORACLE:
                return R.drawable.oracle;
            case MainActivity.MYSQL:
                return R.drawable.mysql;

        }
        return R.drawable.ic_launcher;

    }
    public int getAssociateStatusDrawable(){
        switch((int)answered){
            case 0://not answered
                return R.drawable.unanswered;
            case 1://answered
                return R.drawable.check;
            case 2://closed
                return R.drawable.closed;
        }

        return R.drawable.home;

    }


}
