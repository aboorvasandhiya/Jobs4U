package com.example;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;





import android.os.Bundle;

public class Account extends AppCompatActivity {

    private EditText username;
    private EditText useremail;
    private EditText userage;
    private EditText usernumber;
    private EditText userdisability;
    private EditText userbday;
    private Button userresume;
    private Button usersave;
    private Button userprofile;
    private ImageView profileImage;
    private Uri imageUri;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference();


    StorageReference reference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        username = findViewById(R.id.name_input);
        useremail = findViewById(R.id.email_input);
        userage = findViewById(R.id.age_input);
        usernumber = findViewById(R.id.number_input);
        userdisability = findViewById(R.id.disability_input);
        userbday = findViewById(R.id.birthday_input);
        userresume = findViewById(R.id.resume_upload);
        usersave = findViewById(R.id.save);
        profileImage = findViewById(R.id.profile_image);

        reference = FirebaseStorage.getInstance().getReference();



        usersave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                String usernameTxt = username.getText().toString();
                String useremailTxt = useremail.getText().toString();
                String userageTxt = userage.getText().toString();
                String usernumberTxt = usernumber.getText().toString();
                String userbdayTxt = userbday.getText().toString();
                String userdisabilityTxt = userdisability.getText().toString();


                if (TextUtils.isEmpty(usernameTxt)) {
                    username.setError("Required");
                    return;
                } else if (TextUtils.isEmpty(useremailTxt)) {
                    useremail.setError("Required");
                    return;
                } else if (TextUtils.isEmpty(userageTxt)) {
                    userage.setError("Required");
                    return;
                } else if (TextUtils.isEmpty(usernumberTxt)) {
                    usernumber.setError("Required");
                    return;

                } else if (TextUtils.isEmpty(userbdayTxt)) {
                    userbday.setError("Required");
                    return;
                }
                if (TextUtils.isEmpty(userdisabilityTxt)) {
                    userdisability.setError("Required");
                    return;
                }

                if (imageUri != null) {

                    uploadToFirebase(imageUri);

                } else {
                    Toast.makeText(Account.this, "Please select image", Toast.LENGTH_SHORT).show();
                }


                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("name", usernameTxt);
                userMap.put("email", useremailTxt);
                userMap.put("age", userageTxt);
                userMap.put("phone number", usernumberTxt);
                userMap.put("bday", userbdayTxt);
                userMap.put("disability", userdisabilityTxt);
                root.push().setValue(userMap);


            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);


            }
        });


        userresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            imageUri=data.getData() ;
            profileImage.setImageURI(imageUri);


        }

        if (requestCode == 12 && resultCode == RESULT_OK && data != null){

            uploadPDFFileFirebase(data.getData());

        }
    }

    private void uploadPDFFileFirebase(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this) ;
        progressDialog.setTitle("File is loading");
        progressDialog.show() ;
        StorageReference ref= reference.child("upload"+System.currentTimeMillis()+".pdf");

        ref.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF= new putPDF(userresume.getText().toString(),uri.toString());
                        root.child(root.push().getKey()).setValue(putPDF) ;
                        Toast.makeText(Account.this, "File Upload", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded"+(int)progress+"%");

                    }
                });

    }


    private void uploadToFirebase(Uri uri ){

        StorageReference fileRef = reference.child(System.currentTimeMillis() +"."+ getFileExtension(uri)) ;
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        profpic profpic = new profpic(uri.toString());
                        String profpicId = root.push().getKey();
                        root.child(profpicId).setValue(profpic);
                        Toast.makeText(Account.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                    }
                }) ;

            }


        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Account.this," Uploading Failed !",Toast.LENGTH_SHORT).show();
            }
        }) ;


    }
    private String getFileExtension (Uri mUri) {

        ContentResolver cr = getContentResolver() ;
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri)) ;

    }
}









