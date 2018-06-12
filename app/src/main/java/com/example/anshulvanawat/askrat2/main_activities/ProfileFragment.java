package com.example.anshulvanawat.askrat2.main_activities;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

public class ProfileFragment extends Fragment {


    private static int RESULT_LOAD_IMAGE = 1;

    TextView EmailTextView;
    TextView ContactNoTextView;
    TextView genderView;
    TextView userNameView;
    TextView myQuestionListView;
    TextView ratIdTextView;


    ImageButton userImageButton;
    static TextView dateView;

    Button submit;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        MainActivity.CURRENT_FRAGMENT=MainActivity.PROFILE_FRAGMENT;//used to change the action bar option
        MainActivity.refreshActionBarMenu(getActivity());//user defined method

        dateView = (TextView)view.findViewById(R.id.dateOfBirthTextView);
        EmailTextView=(TextView)view.findViewById(R.id.emailId);
        ContactNoTextView=(TextView)view.findViewById(R.id.contactno);
        genderView=(TextView) view.findViewById(R.id.genderTextView);
        userNameView=(TextView)view.findViewById(R.id.userNameText);
        myQuestionListView=(TextView)view.findViewById(R.id.my_questions);
        ratIdTextView=(TextView)view.findViewById(R.id.ratidText);

        userImageButton=(ImageButton)view.findViewById(R.id.userImageButton);
        submit=(Button)view.findViewById(R.id.profileSubmit);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());

        final String ratid=preferences.getString(MainActivity.RATID,"NAN");
        final String firstname=preferences.getString(MainActivity.FIRST_NAME,"NAN");
        final String lastname=preferences.getString(MainActivity.LAST_NAME,"NAN");
        final String email=preferences.getString(MainActivity.EMAIL,"noEmail");
        final String password=preferences.getString(MainActivity.PASSWORD,"NAN");
        final String contactno=preferences.getString(MainActivity.CONTACTNO,"noContact");
        String gender=preferences.getString(MainActivity.GENDER,"NAN");
        String dateofbirth=preferences.getString(MainActivity.DATEOFBIRTH,"NAN");

        String userName=firstname.substring(0,1).toUpperCase()+firstname.substring(1)+" "+lastname;//capitalizing firstletter of name
        userNameView.setText(userName);
        EmailTextView.setText(email);
        ContactNoTextView.setText(contactno);
        ratIdTextView.setText(ratid);
        if(!gender.equals("NAN")){
            genderView.setText(gender);
        }else{
            genderView.setText("Click to change");
        }

        if(dateofbirth.equals("NAN")){
            dateView.setText("Click to change");
        }else{
            dateView.setText(dateofbirth);
        }

        genderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.custom_dialog);
               // dialog.setTitle("Choose your gender");

                TextView maleView=(TextView) dialog.findViewById(R.id.maleTextView);
                TextView femaleView=(TextView) dialog.findViewById(R.id.femaleTextView);
                ImageView maleimageView=(ImageView)dialog.findViewById(R.id.maleImgView);
                ImageView femaleimageView=(ImageView)dialog.findViewById(R.id.femaleImgView);

                maleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        genderView.setText("Male");
                        dialog.dismiss();
                    }
                });
                maleimageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        genderView.setText("Male");
                        dialog.dismiss();
                    }
                });
                femaleimageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        genderView.setText("Female");
                        dialog.dismiss();
                    }
                });
                femaleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        genderView.setText("Female");
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }


        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gender1=genderView.getText().toString();
                String dateofBirth1=dateView.getText().toString();

                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(MainActivity.GENDER,gender1);
                editor.putString(MainActivity.DATEOFBIRTH,dateofBirth1);
                editor.apply();

                if(gender1.equals("Click to change")){
                    gender1="NAN";
                }
                if(dateofBirth1.equals("Click to change")){
                    dateofBirth1="NAN";
                }
                WebConnector2 webConnector2=new WebConnector2();
                String check="false";//for updating the database

                Boolean debug= webConnector2.registration(check,ratid,firstname,lastname,email,contactno,dateofBirth1,gender1,password);

                if(debug){
                    Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Profile Not Updated",Toast.LENGTH_SHORT).show();
                }




            }
        });


        myQuestionListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                MainActivity.PREVIOUS_FRAGMENT_LIST.add(MainActivity.CURRENT_FRAGMENT);
                MainActivity.CURRENT_FRAGMENT=MainActivity.MY_QUESTION_FRAGMENT;//used to change the action bar option

                MyQuestionListFragment myQuestionListFragment =new MyQuestionListFragment();
                getActivity().setTitle("My Questions");

                fragmentTransaction.replace(R.id.fragment_main,myQuestionListFragment,MainActivity.MY_QUESTION_LIST_FRAGMENT_TAG)
                        .addToBackStack(MainActivity.MY_QUESTION_LIST_FRAGMENT_TAG);
                fragmentTransaction.commit();


            }
        });
        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });
        userNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });


        return view;
    }





    public static void showDate(int year, int month, int day) {//called by main activity date picker class
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                String pictureextn = picturePath.substring(picturePath.lastIndexOf(".")+1);

                if (pictureextn.equals("img") || pictureextn.equals("jpg") || pictureextn.equals("jpeg") ||
                        pictureextn.equals("gif") || pictureextn.equals("png")) {



                } else {
                    Toast.makeText(getActivity(), "Image format not supported, try img,jpg,jpeg,gif,png", Toast.LENGTH_LONG).show();
                }

                cursor.close();


                userImageButton.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT)
                    .show();
        }

    }
}
