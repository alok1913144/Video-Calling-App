package com.jpr.videocall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    EditText secretCodeBox;
    Button joinBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        secretCodeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess= secretCodeBox.getText().toString();

                if(!TextUtils.isEmpty(mess)){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Secret Code for Meeting is "+mess);
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);


                }
                else {
                    Toast.makeText(DashboardActivity.this, "Please fill secret code", Toast.LENGTH_SHORT).show();
                }

            }
        });

        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeBox=secretCodeBox.getText().toString();

                if(!TextUtils.isEmpty(codeBox)){
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(secretCodeBox.getText().toString())
                            .setWelcomePageEnabled(false)
                            .build();

                    JitsiMeetActivity.launch(DashboardActivity.this, options);

                }
                else {
                    Toast.makeText(DashboardActivity.this, "Please fill secret code", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}