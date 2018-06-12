package com.example.anshulvanawat.askrat2.starting_activities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;

import java.util.Random;


/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity{

    public static final String RATID="com.example.anshulvanawat.askrat.RATID";
    public static final String FIRSTNAME="com.example.anshulvanawat.askrat.FIRSTNAME";
    public static final String LASTNAME="com.example.anshulvanawat.askrat.LASTNAME";
    public static final String EMAIL="com.example.anshulvanawat.askrat.EMAIL";
    public static final String PASSWORD="com.example.anshulvanawat.askrat.PASSWORD";
    public static final String DATE="com.example.anshulvanawat.askrat.DATE";


    EditText firstNameView;
    EditText lastNameView;
    EditText emailView;
    EditText contactNoView;
    EditText passwordView;
    EditText password2View;
    Button signUpbtn;


    private UserLoginTask mAuthTask = null;

    private View mProgressView;
    private View mLoginFormView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //setting background here to solve scroll view problem
        getWindow().setBackgroundDrawableResource(R.drawable.bg2) ;

        firstNameView=(EditText)findViewById(R.id.firstName);
        lastNameView =(EditText)findViewById(R.id.lastName);
        emailView=(EditText)findViewById(R.id.email);
        contactNoView=(EditText)findViewById(R.id.signUpContactNo);
        passwordView=(EditText)findViewById(R.id.password);
        password2View =(EditText)findViewById(R.id.password2);
        signUpbtn=(Button)findViewById(R.id.signUp);
        signUpbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

               // WebConnector2 wc=new WebConnector2();
                //check for network connection

                //check for internet connection
              //  Boolean internetCheck=wc.isInternetAvailable();

              //  if( internetCheck) {
                    attemptSignUp();
              /* }else{
                    Toast.makeText(getApplication(),"No Internet Connection",Toast.LENGTH_LONG).show();
                }
               */
            }
        });


        mLoginFormView = findViewById(R.id.signup_form);
        mProgressView = findViewById(R.id.signup_progress);

    }


    private void attemptSignUp() {

        if (mAuthTask != null) {
            return;
        }

        String firstname=firstNameView.getText().toString();
        String lastname=lastNameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String password2=password2View.getText().toString();
        String contactNo=contactNoView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        }else if(!isPasswordValid(password)){
            passwordView.setError("Password length between 8 to 50 charcters");
            focusView = passwordView;
            cancel = true;
        }else if(!isPasswordValid(password,password2)){
            password2View.setError("Password do not match");
            focusView = password2View;
            cancel = true;
        }
        if(TextUtils.isEmpty(firstname)){
            firstNameView.setError(getString(R.string.error_field_required));
            focusView = firstNameView;
            cancel = true;
        }
        if(TextUtils.isEmpty(lastname)){
            lastNameView.setError(getString(R.string.error_field_required));
            focusView=lastNameView;
            cancel = true;
        }



        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        } else if (isEmailRegistered(email)) {
            emailView.setError(getString(R.string.error_existing_email));
            focusView = emailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(contactNo)) {
            contactNoView.setError(getString(R.string.error_field_required));
            focusView = contactNoView;
            cancel = true;
        } else if (!isContactValid(contactNo)) {
            contactNoView.setError(getString(R.string.error_invalid_ContactNo));
            focusView = contactNoView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            showProgress(true);

            mAuthTask = new UserLoginTask(firstname,lastname,email,contactNo, password);
            mAuthTask.execute((Void) null);


        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isPasswordValid(String password,String password2){

        if(password.equals(password2)){
            return true;
        }
        else return false;

    }
    private boolean isPasswordValid(String password){

        if((password.length()>=8) && (password.length()<=50)){
            return true;
        }
        else return false;

    }
    private boolean isEmailValid(String email){
         if(email.contains("@")&& email.contains(".") && !email.contains(" ")){
             return true;
         }
        else return false;
    }
    private boolean isEmailRegistered(String email) {

        WebConnector2 wc=new WebConnector2();
        try {
           return wc.checkEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        return true;//to stop from signing up



    }



    private boolean isContactValid(String contactNo){
        if(contactNo.length()==10){
            return true;
        }
        else return false;
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {


        private final String mRatid;
        private final String mFirstname;
        private final String mLastname;
        private final String mEmail;
        private final String mContactNo;
        private final String mPassword;

        UserLoginTask(String firstname, String lastname ,String email,String contactNo, String password) {

            String ratid=firstname+getRandomNoOf6Digits()+lastname;//generating random ratid

            mRatid=ratid;
            mFirstname=firstname;
            mLastname=lastname;
            mEmail = email;
            mContactNo=contactNo;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            //attempt registering
            WebConnector2 wc=new WebConnector2();
            String mCheck="true"; //for creating a new entry
            String gender="NAN";
            String dateofbirth="NAN";
            return wc.registration(mCheck,mRatid,mFirstname,mLastname,mEmail,mContactNo,dateofbirth,gender,mPassword);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                Toast.makeText(getApplicationContext(),"Successfully Signed up,You can Sign in now",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra(EMAIL,mEmail);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(),"Sorry, Something is wrong, NOT REGISTERED",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    private String getRandomNoOf6Digits(){

        Random random=new Random();
        String randomNo="";
        for(int i=0;i<6;i++){
            randomNo =randomNo+(random.nextInt(9));
        }

        return randomNo;


    }




}