package com.example.instacalcid1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;


public class chatfragment extends Fragment {

    String appID = "3451125e61cd142"; // Replace with your App ID
    String region = "us";// Replace with your App Region ("eu" or "us")


    public chatfragment() {

    }


    public static chatfragment newInstance(String param1, String param2) {
        chatfragment fragment = new chatfragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initCometChat();
        login();


        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatfragment, container, false);
    }


    private void initCometChat(){


        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();

        CometChat.init(getContext(), appID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
             //   Log.d(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
              //  Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });
    }

    private void login(){
        String UID = "user1"; // Replace with the UID of the user to login
        String authKey = "140b3f7acc1938d4768b86ec71559e7485d6e635"; // Replace with your App Auth Key

        if (CometChat.getLoggedInUser() == null) {
            CometChat.login(UID, authKey, new CometChat.CallbackListener<User>() {

                @Override
                public void onSuccess(User user) {
                  //  Log.d(TAG, "Login Successful : " + user.toString());
                }

                @Override
                public void onError(CometChatException e) {
                  // Log.d(TAG, "Login failed with exception: " + e.getMessage());
                }
            });
        } else {
            // User already logged in
        }
    }
}