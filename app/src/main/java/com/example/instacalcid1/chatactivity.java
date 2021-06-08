package com.example.instacalcid1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;

public class chatactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);


         // Replace with your App ID
         // Replace with your App Region ("eu" or "us")

//        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();
//
//        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
//            @Override
//            public void onSuccess(String successMessage) {
//
//            }
//            @Override
//            public void onError(CometChatException e) {
//
//            }
//        });
    }

//    private void initComet(){
//        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();
//
//        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {
//            @Override
//            public void onSuccess(String successMessage) {
//                Log.d(TAG, "Initialization completed successfully");
//            }
//            @Override
//            public void onError(CometChatException e) {
//                Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
//            }
//        });
//    }
}