package com.example.onzzz.loginsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("Name");
        final String id = intent.getStringExtra("Id");
        final String profilePicUri = intent.getStringExtra("ProfilePicUri");
        final String loginMethod = intent.getStringExtra("LoginMethod");

        Button displayInfo = (Button) findViewById(R.id.display_user_info_button);
        displayInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView loginMethodText = (TextView) findViewById(R.id.login_method);
                loginMethodText.setVisibility(View.VISIBLE);
                if (loginMethod.equals("Facebook")){
                    loginMethodText.setText("You are logged in using Facebook");
                    //ProfilePictureView displayProfilePic = (ProfilePictureView) findViewById(R.id.profilePicFacebook);
                    ImageView displayProfilePic = (ImageView) findViewById(R.id.profilePicFacebook);
                    displayProfilePic.setVisibility(View.VISIBLE);
                    new LoadProfileImage(displayProfilePic).execute(profilePicUri);
                }
                else if (loginMethod.equals("Google")){
                    loginMethodText.setText("You are logged in using Google");
                    ImageView displayProfilePic = (ImageView) findViewById(R.id.profilePicGoogle);
                    displayProfilePic.setVisibility(View.VISIBLE);
                    new LoadProfileImage(displayProfilePic).execute(profilePicUri);
                }
                TextView displayName = (TextView) findViewById(R.id.userName);
                displayName.setVisibility(View.VISIBLE);
                displayName.setText(name);
            }
        });
    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
