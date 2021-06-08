package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup_activity extends AppCompatActivity {

    Button signup;
    EditText name, email, password, phone, retypepassword;
    TextView haveanaccount;
    String usercount;
   CircleImageView signup_addimage;
    Uri filepath ;
    Bitmap bitmap ;
    boolean image_added = false;

    FirebaseDatabase  rootnode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootnode.getReference("users");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        // hook views
        signup = this.findViewById(R.id.signupactivity_signup_button);
        signup_addimage = this.findViewById(R.id.signup_addimage);
        name = this.findViewById(R.id.signupactivity_PersonName);
        email = this.findViewById(R.id.signupactivity_EmailAddress);
        phone = this.findViewById(R.id.signupactivity_typephone);
        password = this.findViewById(R.id.signupactivity_typepassword);
        retypepassword = this.findViewById(R.id.signupactivity_retypepassword);
        haveanaccount = this.findViewById(R.id.signupactivity_haveanaccount);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidekeyboard();

//                Intent intent = new Intent(getApplicationContext(),veirfy_activity.class);
//                startActivity(intent);
                isUser();

            }
        });

        signup_addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                         {
                            Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");

                            startActivityForResult(intent.createChooser(intent, "please select image"), 1);

                            Dexter.withActivity(signup_activity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent.createChooser(intent, "please select Profile Image"), 1);

                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    Toast.makeText(getApplicationContext(), "permission asked", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } }
                });



    haveanaccount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent (getApplicationContext(),login_activity.class);
            startActivity(intent);
        }
    });
    }

    private Boolean validateUsername() {

        String entry = name.getText().toString();

        if (entry.isEmpty()) {
            name.setError("name cannot be empty");
            return false;
        } else if (entry.length() > 10) {
            name.setError("username too long");
            return false;
        } else {
            name.setError(null);
            return true;
        }

    }

    private Boolean validatephone() {

        String entry = phone.getText().toString();
        if (entry.isEmpty()) {
            phone.setError("phone cannot be empty");
            return false;
        } else if (entry.length() != 10) {
            phone.setError("type complete phone no.");
            return false;
        } else {
            phone.setError(null);
            return true;
        }

    }

    private Boolean validateemail() {

        String entry = email.getText().toString();
        String emailpattern = "[a-zA-Z0-9._-]+\\@+[a-z]";

        if (entry.isEmpty()) {
            name.setError("email cannot be empty");
            return false;
        } else {
            email.setError(null);
            return true;
        }

    }

    private Boolean validatepassword() {

        String entry = password.getText().toString();

        if (entry.isEmpty()) {
            password.setError("password  cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }

    private Boolean validateretypepassword() {

        String entry1 = password.getText().toString();
        String entry2 = retypepassword.getText().toString();

        if (!entry1.equals(entry2)) {
            retypepassword.setError("entry dont match");
            return false;
        }
        else {
            retypepassword.setError(null);
            return true;
        }

    }

    private void registeruser() {

           if( !validatephone() | !validateretypepassword() | !validateUsername() ) return;
           //  !validateretypepassword()


        createnew_user newuser_data = new createnew_user(name.getText().toString(),
                email.getText().toString(),
                password.getText().toString(), phone.getText().toString());

        if(image_added) uploadimage();
//
//            if(phone.getText().length() > 0){
//                // Toast.makeText(signup_activity.this,"all are ok",Toast.LENGTH_SHORT).show();
//                createnewuser userdatahelpervalues = new createnewuser(
//                      name.getText().toString(),
//                      email.getText().toString(),
//                      password.getText().toString(),
//                 "","");
//
//               // Intent intent = new Intent(getApplicationContext(),veirfy_activity.class);
//                //intent.putExtra("phoneno",phone.getText());
//                //startActivity(intent);
//

        reference.child(phone.getText().toString()).setValue(newuser_data);



        Snackbar snackbar ;
        View view = findViewById(R.id.signupactivity_signup_button);
        snackbar = Snackbar.make(view,"you signed up succesfully", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();



        //Intent intent = new Intent(getApplicationContext(),login_activity.class);
        //startActivity(intent);


    }

    private void this_user_id() {

        DatabaseReference usercountdatabase = FirebaseDatabase.getInstance().getReference("usercount");
        Query checkUser = usercountdatabase.orderByKey().equalTo("usercount");

        //Toast.makeText(this,usercount ,Toast.LENGTH_SHORT).show();

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    //Toast.makeText(login_activity.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
                    usercount = snapshot.getValue(String.class);
                    //Toast.makeText(login_activity.this,passwordfromdb,Toast.LENGTH_SHORT).show();

                    //String passwordfromdb = snapshot.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void hidekeyboard(){
        View view = this.getCurrentFocus();

        // this will give us the view
        // which is currently focus
        // in this layout


        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(
                    view.getWindowToken(), 0);
        }
    }


    private void isUser(){

        String userenterenphoneno= phone.getText().toString().trim();

        DatabaseReference databaseReferencevalue = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = databaseReferencevalue.orderByKey().equalTo(userenterenphoneno);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    phone.setError("this phone no. is already registered");
                    phone.requestFocus();
                    Toast.makeText(signup_activity.this,snapshot.toString(),Toast.LENGTH_SHORT).show();

                    //phone.setErrorEnabled(false);
                    //String passwordfromdb = snapshot.child(userenterenphoneno).child("password").getValue(String.class);

                }
                else {
                    phone.setError(null);

                   registeruser();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK){

            filepath = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                signup_addimage.setImageBitmap(bitmap);
                //gallery_add.setImageResource(R.drawable.outline_remove_circle_24);
                image_added= true ;
                Toast.makeText(signup_activity.this,String.valueOf(image_added), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"error in loading image", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadimage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        Toast.makeText(getApplicationContext(),"profile uploaded",Toast.LENGTH_SHORT).show();

        StorageReference uploader = storage.getReference().child(phone.getText().toString()+"p0");
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"filesuplosded",Toast.LENGTH_SHORT).show();
               // databaseReferencevalue.child(applicationStatus.getPhone()).child("totalpost_count").setValue(nextpostcount);
                //pplicationStatus.setTotalpost_count(nextpostcount);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        });
        Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
    }

}
