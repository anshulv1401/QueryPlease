package com.example.anshulvanawat.askrat2;

import android.os.StrictMode;



import com.example.anshulvanawat.askrat2.answer_activities.Ans;
import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.main_activities.Que;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;



/**
 * Created by Anshul vanawat on 23-07-16.
 */
public class WebConnector2{

    public static String url = "http://192.168.43.242/askrat2/";//192.168.0.139 do not give any space in th url

    public Boolean checkConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url=this.url+"checkConnection.php";

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair(MainActivity.CHECK, "status"));


        try {
            HttpResponse response=sendData(list, url);
            String result=getDataFromResponse(response);
            if(result.contains("connected")) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public Boolean registration(String check,String ratid, String firstname, String lastname,
                                   String email,String contactNo,String dateOfBirth,String gender, String password) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair(MainActivity.CHECK, check));//for creating or updating a entry, true for creating and false for updating
        list.add(new BasicNameValuePair(MainActivity.RATID, ratid));
        list.add(new BasicNameValuePair(MainActivity.FIRST_NAME, firstname));
        list.add(new BasicNameValuePair(MainActivity.LAST_NAME, lastname));
        list.add(new BasicNameValuePair(MainActivity.EMAIL, email));
        list.add(new BasicNameValuePair(MainActivity.CONTACTNO, contactNo));//contact
        list.add(new BasicNameValuePair(MainActivity.DATEOFBIRTH, dateOfBirth));
        list.add(new BasicNameValuePair(MainActivity.GENDER, gender));
        list.add(new BasicNameValuePair(MainActivity.PASSWORD, password));
        list.add(new BasicNameValuePair(MainActivity.DATEINMILLIS, Calendar.getInstance().getTimeInMillis() + ""));




        String url = this.url + "registration.php";

        try {
            HttpResponse response=sendData(list, url);
            String result=getDataFromResponse(response);
            if(result.contains("true")) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String checkUser(String email, String password) throws Exception {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        List<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(MainActivity.EMAIL, email));
        list.add(new BasicNameValuePair(MainActivity.PASSWORD, password));

        String url = this.url + "login.php";


        try {
            HttpResponse response = sendData(list, url);
            result = getDataFromResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }




        try {
            String userInfoArray=getUserInfo(result);
            return userInfoArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Exception();


    }

    public boolean checkEmail(String email) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";


        List<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(MainActivity.EMAIL, email));

        String url = this.url + "checkEmail.php";


        try {
            HttpResponse response = sendData(list, url);
            result = getDataFromResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(result.contains("false")) {//email not registered
            return false;
        }else {
            return true;//email is registered
        }

    }

    public String getUserInfo(String result) throws Exception {

        try {

            JSONObject jobj=new JSONObject(result);
            JSONArray jarray=jobj.getJSONArray("ratstudent");
            String sarray;
                sarray=jarray.getJSONObject(0).get(MainActivity.RATID).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.FIRST_NAME).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.LAST_NAME).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.EMAIL).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.PASSWORD).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.DATEINMILLIS).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.DATEOFBIRTH).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.CONTACTNO).toString()
                        +"~"+jarray.getJSONObject(0).get(MainActivity.GENDER).toString();

            return sarray;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }catch (Exception e){
            e.printStackTrace();
        }
        throw new Exception();
    }


    public boolean postQuestion(String studentName, String subject, String topic, String question, String description,
                                   String ratid, String noOfVisitors, String answered,String imagepath,String check) {
        //check is used to check weather the entry is created or updated, true for creating and false for updating
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String url = this.url + "insertQuestion.php";

        List<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(MainActivity.CHECK, check));
        list.add(new BasicNameValuePair(MainActivity.STUDENT_NAME, studentName));
        list.add(new BasicNameValuePair(MainActivity.SUBJECT, subject));
        list.add(new BasicNameValuePair(MainActivity.TOPIC, topic));
        list.add(new BasicNameValuePair(MainActivity.QUESTION, question));
        list.add(new BasicNameValuePair(MainActivity.DESCRIPTION, description));
        list.add(new BasicNameValuePair(MainActivity.RATID, ratid));
        list.add(new BasicNameValuePair(MainActivity.NO_OF_VISITORS, noOfVisitors));
        list.add(new BasicNameValuePair(MainActivity.ANSWERED, answered));
        list.add(new BasicNameValuePair(MainActivity.IMGAGE_PATH,imagepath));
        list.add(new BasicNameValuePair(MainActivity.DATEINMILLIS, Calendar.getInstance().getTimeInMillis() + ""));



        try {
            HttpResponse response = sendData(list, url);
            result = getDataFromResponse(response);



        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.contains("success")) {//there is nothing in result, find a way to make it work......
            return true;
        }
        return false;

    }

    public ArrayList<Que> getQueById(String id) throws Exception {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String url = this.url + "getQuestionById.php";


        List<NameValuePair> list = new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(MainActivity.QUESTION_ID,id));

        HttpResponse response = null;
        try {
            response = sendData(list, url);
            result = getDataFromResponse(response);

            return getQuestions(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Exception();
    }




    public ArrayList<Que> retrieveQuestionsString(String subject, String topic) throws Exception {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String url = this.url + "getQuestion.php";


        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if(topic.trim().equals(MainActivity.All.trim())){//if 'all' option is selected then all the question related to
                                                                // that subject
            topic="all";
        }
        //using it in question list and my question list fragment
        if(subject.trim().equals(MainActivity.NOT_SUBJECT_BUT_RATID.trim())) {//using this method two ways, first to get questions for subject and topic
                                                                //and other to get question for  ratid
            String ratid=topic;
            list.add(new BasicNameValuePair(MainActivity.RATID, ratid));
            list.add(new BasicNameValuePair(MainActivity.SUBJECT, null));
            list.add(new BasicNameValuePair(MainActivity.TOPIC, null));
        }else {
            list.add(new BasicNameValuePair(MainActivity.RATID, null));
            list.add(new BasicNameValuePair(MainActivity.SUBJECT, subject));
            list.add(new BasicNameValuePair(MainActivity.TOPIC, topic));
        }

        HttpResponse response = null;
        try {
            response = sendData(list, url);
            result = getDataFromResponse(response);

            return getQuestions(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Exception();
    }


    public boolean postAnswer(String studentName, String queId,String answer,
                                 String ratid,String imagepath,String check) {//status tell whether answer is ticked correct or not by the admin
        //noOfCheck will tell no. of people think that this is a correct answer


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result = "";
        String url=this.url+"insertAnswer.php";


        List<NameValuePair> list = new ArrayList<>();

        list.add(new BasicNameValuePair(MainActivity.CHECK, check));
        list.add(new BasicNameValuePair(MainActivity.STUDENT_NAME, studentName));
        list.add(new BasicNameValuePair(MainActivity.QUESTION_ID, queId));
        list.add(new BasicNameValuePair(MainActivity.ANSWER, answer));
        list.add(new BasicNameValuePair(MainActivity.RATID, ratid));
        list.add(new BasicNameValuePair(MainActivity.NO_OF_CHECK, 0+""));
        list.add(new BasicNameValuePair(MainActivity.STATUS,0+""));
        list.add(new BasicNameValuePair(MainActivity.IMGAGE_PATH,imagepath));
        list.add(new BasicNameValuePair(MainActivity.DATEINMILLIS, Calendar.getInstance().getTimeInMillis() + ""));





        HttpResponse response = null;
        try {
            response = sendData(list, url);
            result = getDataFromResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result.contains("success")) {
            return true;
        }
        return false;

    }


    public ArrayList<Ans> retrieveAnswersString(String QueId) throws Exception {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result;
        String url = this.url + "getAnswers.php";


        List<NameValuePair> list = new ArrayList<>();

        list.add(new BasicNameValuePair(MainActivity.QUESTION_ID, QueId));


        HttpResponse response;
        try {
            response = sendData(list, url);
            result = getDataFromResponse(response);

            return getAnswers(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new Exception();
    }


    public HttpResponse sendData(List<NameValuePair> list, String url) throws Exception {
        try {


            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse response = httpclient.execute(httppost);//getting org.apache.http.conn.HttpHostConnectException here

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("something went wrong");

        }
        throw new Exception();

    }


    public String getDataFromResponse(HttpResponse response) throws Exception {
        try {


            InputStream is = response.getEntity().getContent();


            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            }
        } catch (ClientProtocolException e) {
            System.out.println("fail Client failure");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("fail IO failure");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong");

        }
        throw new Exception();


    }

    public ArrayList<Que> getQuestions(String result) throws Exception {

        try
        {
            JSONObject jobj = new JSONObject(result);
            JSONArray jarray = jobj.getJSONArray("questions");

            ArrayList<Que> sarray=new ArrayList<>();

            for (int i = 0; i < jarray.length(); i++) {
                String str=jarray.getJSONObject(i).get(MainActivity.ID).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.STUDENT_NAME).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.SUBJECT).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.TOPIC).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.QUESTION).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.DESCRIPTION).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.RATID).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.NO_OF_VISITORS).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.ANSWERED).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.IMGAGE_PATH).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.DATEINMILLIS).toString();

                Que que=getQue(str);

                sarray.add(que);
            }

            return sarray;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        throw new Exception();

    }

    public ArrayList<Ans> getAnswers(String result) throws Exception {
        try
        {
            JSONObject jobj = new JSONObject(result);
            JSONArray jarray = jobj.getJSONArray("answers");

            ArrayList<Ans> sarray=new ArrayList<>();

            for (int i = 0; i < jarray.length(); i++) {
                String str=jarray.getJSONObject(i).get(MainActivity.ID).toString()
                        + "~" +jarray.getJSONObject(i).get(MainActivity.STUDENT_NAME).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.QUESTION_ID).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.ANSWER).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.RATID).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.IMGAGE_PATH).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.NO_OF_CHECK).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.STATUS).toString()
                        + "~" + jarray.getJSONObject(i).get(MainActivity.DATEINMILLIS).toString();
                Ans ans=getAns(str);
                sarray.add(ans);

            }
            return sarray;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        throw new Exception();


    }

    public Que getQue(String questionString){

        StringTokenizer st=new StringTokenizer(questionString,"~",false);

        String QueId=st.nextToken().trim();
        String studentname=st.nextToken().trim();
        String subject=st.nextToken().trim();
        String topic=st.nextToken().trim();
        String question=st.nextToken().trim();
        String description=st.nextToken().trim();
        String ratid=st.nextToken().trim();
        String noofvisitors=st.nextToken().trim();
        String answered=st.nextToken().trim();
        String imagepath=st.nextToken().trim();
        String dateinmillis=st.nextToken().trim();

        Que que=new Que(QueId,studentname,subject,topic,question,description,ratid,noofvisitors,answered,imagepath,dateinmillis);


        return que;


    }
    public Ans getAns(String answerString){

        StringTokenizer st=new StringTokenizer(answerString,"~",false);

        String AnsId=st.nextToken().trim();
        String studentname=st.nextToken().trim();
        String QuestionId=st.nextToken().trim();
        String answer=st.nextToken().trim();
        String ratid=st.nextToken().trim();
        String imagepath=st.nextToken().trim();
        String noofcheck=st.nextToken().trim();
        String status=st.nextToken().trim();
        String dateinmillis=st.nextToken().trim();

        Ans ans=new Ans(AnsId,studentname,QuestionId,answer,ratid,imagepath,noofcheck,status,dateinmillis);
        return ans;


    }


    public String[][] getSubjects(){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url=this.url+"getSubjects.php";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        String[][] sarray=new String[][]{{"Error while"},{"getting data"}};
        try {
            HttpResponse response = httpclient.execute(httppost);//getting org.apache.http.conn.HttpHostConnectException here


            String result=getDataFromResponse(response);


            JSONObject jobj=new JSONObject(result);
            JSONArray jarray=jobj.getJSONArray("subjects");
            sarray=new String[jarray.length()][3];//in 2nd perameter 0 for id,1 for subject name, 2 for iconpath
            for(int i=0;i<jarray.length();i++) {
                sarray[i][0] = jarray.getJSONObject(i).get("id").toString();
                sarray[i][1] = jarray.getJSONObject(i).get("subjectName").toString();
                sarray[i][2] = jarray.getJSONObject(i).get("iconpath").toString();
            }
            return sarray;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return sarray;

    }




    public String[] getTopics(String subject){


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("subject", subject));

        String url=this.url+"getTopics.php";
        String[] sarray=new String[]{"Error"};

        String result="";

        try {
            HttpResponse response = sendData(list, url);
            result = getDataFromResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            JSONObject jobj=new JSONObject(result);
            JSONArray jarray=jobj.getJSONArray("topics");
            sarray=new String[jarray.length()+2];//adding 2 options extra, se
            sarray[0]=MainActivity.All;//Adding option 'select_topic' to all the list
            for(int i=1;i<jarray.length()+1;i++) {
                sarray[i] = jarray.getJSONObject(i-1).get("topics").toString();
            }
            sarray[jarray.length()+1]=MainActivity.OTHER;//adding 'other' option
            return sarray;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return sarray;



    }



}

