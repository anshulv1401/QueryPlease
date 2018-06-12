package com.example.anshulvanawat.askrat2.answer_activities;

import com.example.anshulvanawat.askrat2.R;

import java.util.Calendar;

/**
 * Created by anshul vanawat on 7/30/2016.
 */
public class Ans {

    private String studentName;
    private String answer;
    private String imagepath;

    private long ansId;
    private long questionId;
    private String ratid;
    private long noOfCheck;
    private long status;
    private long dateinMilli;
    //status is used to store a no.,0 for not checked, 1 for correct,2 incorrect
    //questionId is used to find the answers of the specific question


    public Ans(String ansid, String studentName, String questionid, String answer, String ratid,String imagepath, String noOfCheck,
               String status, String dateInMilli){

        this.ansId=Long.parseLong(ansid);
        this.studentName=studentName;
        this.questionId=Long.parseLong(questionid);
        this.answer=answer;
        this.ratid=ratid;
        this.noOfCheck=Long.parseLong(noOfCheck);
        this.status=Long.parseLong(status);
        this.imagepath="NAN";
        this.dateinMilli=Long.parseLong(dateInMilli);


    }


    public Ans(String studentName, String questionid, String answer,String description){

        this.ansId=0;
        this.studentName=studentName;
        this.questionId=0;
        this.answer=answer;
        this.ratid=0+"";
        this.noOfCheck=0;
        this.status=0;
        this.imagepath="NAN";
        this.dateinMilli = Calendar.getInstance().getTimeInMillis();

    }




    public String getStudentName(){return studentName;}
    public String getAnswer(){return answer;}
    public String getImagepath(){return imagepath;}
    public long getAnsId(){return ansId;}
    public long getQuestionId(){return questionId;}
    public String getRatid(){return ratid;}
    public long getNoOfCheck(){return noOfCheck;}
    public long getStatus(){return status;}
    public long getDateCreatedMilli(){return dateinMilli;}



    public int getAssociateStatusDrawable(){
        switch((int)status){
            case 0://not checked
                return R.drawable.home;
            case 1://correct answer
                return R.drawable.check;
        }

        return R.drawable.home;

    }

   /* public String toString(){
        return "ID: "+ansId+" StudentName: "+ studentName+" Subject: "+subject+" topic: "
                +topic +" Que: "+que+ " queId: "+queId+" ratid: "+ratid+" no of visitors: "
                + noOfVisitors +" answered: "+answered+" dateCreatedMilli: "+dateCreatedMilli;
    }*/



}
