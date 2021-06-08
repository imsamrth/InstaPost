package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class addstatus_activity extends AppCompatActivity {




    FloatingActionButton removebutton ,uploadstatus,writecaption_fab;
    EditText write_CAPTION;
    VideoView videoView ;
    MediaController mediaController ;
    Uri videouri ;
    StorageReference storageReference ;
    DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstatus_activity);

        videoView = this.findViewById(R.id.videoView);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("status");



        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();
        Intent fetchvideo = new Intent();
        fetchvideo.setType("video/*");
        fetchvideo.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(fetchvideo,1010);
        write_CAPTION  = this.findViewById(R.id.status_caption_edittext);
        write_CAPTION.setVisibility(View.INVISIBLE);
        writecaption_fab = this.findViewById(R.id.writecaption_fab);
        uploadstatus = this.findViewById(R.id.upload_status_fab);
        removebutton= this.findViewById(R.id.remove_button);
        writecaption_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write_CAPTION.setVisibility(View.VISIBLE);
            }
        });

        uploadstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processvideoupload();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1010 ){
            videouri=data.getData();
            videoView.setVideoURI(videouri);
        }
    }

    public String getExtension(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videouri));
    }


    public void processvideoupload(){
        final ProgressDialog pd = new ProgressDialog( this);
        pd.setTitle("status uploaded");
        pd.show();
        StorageReference uploader = storageReference.child("status/"+loggined_user_details.getPhone()+"."+getExtension());
        uploader.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pd.dismiss();
                        new_status new_status = new new_status(write_CAPTION.getText().toString(),uri.toString());
                        databaseReference.child(loggined_user_details.getPhone()).setValue(new_status);
                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
long per = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
pd.setMessage("uploaded "+(int)per+"%" );
            }
        });

    }
}