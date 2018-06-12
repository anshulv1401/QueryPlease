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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.main_activities.MainActivity;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

public class GiveAnswer extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    TextView questionTextView;
    TextView descriptionTextView;
    EditText answerEditView;
   // Button btnImageView2;
    Button postAns;

    String imagepath="NAN";
    String questionId;
    String question;
    String description;
    String studentname;
    String ratId;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_answer);
        questionTextView=(TextView)findViewById(R.id.questionTextView);
        descriptionTextView=(TextView)findViewById(R.id.descriptionTextView);
        answerEditView=(EditText)findViewById(R.id.answerEditText);
       // btnImageView2=(Button)findViewById(R.id.browseImagebtn2);
        postAns=(Button)findViewById(R.id.postAns);



        Intent intent=getIntent();
        questionId=intent.getExtras().getString(MainActivity.QUESTION_ID);
        question=intent.getExtras().getString(MainActivity.QUESTION);
        description=intent.getExtras().getString(MainActivity.DESCRIPTION);

        questionTextView.setText(question);
        descriptionTextView.setText(description);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String firstName=preferences.getString(MainActivity.FIRST_NAME,"DoesNot");
        String lastName=preferences.getString(MainActivity.LAST_NAME,"HaveName");
        studentname=firstName+" "+lastName;
        ratId=preferences.getString(MainActivity.RATID,"0");

     /*   btnImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });
*/
        postAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer=answerEditView.getText().toString().trim();
                if(answer.equals("")){
                    Toast.makeText(getApplication(),"please write answer before posting",Toast.LENGTH_SHORT).show();
                    return;
                }

                WebConnector2 wc=new WebConnector2();
                String check="true";
                if(wc.postAnswer(studentname,questionId,answer,ratId,imagepath,check)) {
                    Toast.makeText(getApplication(),"answer Posted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                Intent returnIntent=new Intent();
                setResult(Activity.RESULT_OK);
                finish();



            }
        });


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
                cursor.close();

              //  ImageView imageView = (ImageView) findViewById(R.id.browseImage);
              //  imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            } else {
                Toast.makeText(getApplication(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplication(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
