package com.example.anshulvanawat.askrat2.other_actitvities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

public class AskQuestion extends AppCompatActivity {

    String studentname;
    String subject=null;
    String topic=null;
    String quetion;
    String description;
    String ratId;
    String imagepath="NAN";

    private static int RESULT_LOAD_IMAGE = 1;

    String topicListAndroid[]={"other Android"};
    String topicListJava[]={"other java"};
    String topicListadv_java[]={"other adv_java"};
    String topicListphp[]={"other php"};
    String topicListmysql[]={"other mysql"};
    String topicListOracle[]={"other oracle"};

    Spinner subjectsSpinner;
    Spinner topicSpinner;

    EditText questionTextView;
    EditText descriptionTextView;

   // Button browseBtnView;
    Button postBtnView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        subjectsSpinner=(Spinner)findViewById(R.id.sujectSpinner);
        topicSpinner=(Spinner)findViewById(R.id.topicSpinner);

        questionTextView=(EditText)findViewById(R.id.questionText);
        descriptionTextView=(EditText)findViewById(R.id.descriptionText);

       // browseBtnView=(Button) findViewById(R.id.browseImagebtn);
        postBtnView =(Button)findViewById(R.id.postQue);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);

        String firstName=preferences.getString(MainActivity.FIRST_NAME,"DoesNot");
        String lastName=preferences.getString(MainActivity.LAST_NAME,"HaveName");

        studentname=firstName+" "+lastName;
        ratId=preferences.getString(MainActivity.RATID,"0");




        //getting subjects from subject array

        final WebConnector2 wc=new WebConnector2();
        String subjectArray[][]=wc.getSubjects();;
        String subjectList[]=new String[subjectArray.length];



        for(int i=0;i<subjectArray.length;i++){

            subjectList[i]=subjectArray[i][1];

        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout, subjectList);

        subjectsSpinner.setAdapter(adapter);

        subjectsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                subject=adapterView.getItemAtPosition(position).toString();

                String topicList[]=wc.getTopics(subject);

                if(topicList==null || topicList.length<=0){
                    topicList=new String[]{"other"};
                }
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getApplication(),R.layout.spinner_layout,topicList);
                topicSpinner.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                topic=adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        postBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                quetion=questionTextView.getText().toString().trim();
                description=descriptionTextView.getText().toString().trim();

                if(quetion.equals("")){
                    Toast.makeText(getApplication(),"enter question please",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(description.equals("")){
                    Toast.makeText(getApplication(),"enter description please",Toast.LENGTH_SHORT).show();
                    return;
                }


                WebConnector2 wc=new WebConnector2();

                String answered=0+"";
                String noofvisitor=0+"";
                String check="true";
                if(wc.postQuestion(studentname,subject,topic,quetion,description,ratId,answered,noofvisitor,imagepath,check)) {
                    Toast.makeText(getApplication(), "Question posted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });

      /*  browseBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });
*/




    }



    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);



    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                String pictureextn = picturePath.substring(picturePath.lastIndexOf(".")+1);

                if (pictureextn.equals("img") || pictureextn.equals("jpg") || pictureextn.equals("jpeg") ||
                        pictureextn.equals("gif") || pictureextn.equals("png")) {



                } else {
                    Toast.makeText(this, "Image format not supported, try img,jpg,jpeg,gif,png", Toast.LENGTH_LONG).show();
                }

                cursor.close();

              //  ImageView imageView = (ImageView) findViewById(R.id.browseImage);
             //   imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            } else {
                Toast.makeText(getApplication(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
