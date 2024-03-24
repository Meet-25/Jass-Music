package com.example.android.jassmusic;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class upload_activity extends AppCompatActivity {


    EditText editText;
    TextView textView;
    Uri audioUri ,imageUri;
    ImageView imageView;
    Button uploadButton, selectButton,selectImage;
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    DatabaseReference referencesong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        editText = findViewById(R.id.song_name);
        textView = findViewById(R.id.no_song_selected);
        uploadButton = findViewById(R.id.upload_song);
        selectButton = findViewById(R.id.Select_song);
        selectImage = findViewById(R.id.Select_image);
        imageView = findViewById(R.id.upload_image);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFile(view);
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAudioFile(view);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAudioToFirebase(view);
            }
        });
        referencesong = FirebaseDatabase.getInstance().getReference().child("songs");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("songs");
    }

    void openImageFile(View v){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 100);
    }

    void openAudioFile(View v) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data.getData() != null) {
            audioUri = data.getData();
            String fileName = getFileName(audioUri);
            textView.setText(fileName);
        }
        if(requestCode == 100 && resultCode == RESULT_OK && data.getData() != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private String getFileName(Uri audioUri) {
        String result = null;
        if (audioUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(audioUri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    } else {
                        // Handle case where DISPLAY_NAME column is not present
                        result = "Unknown"; // or some default name
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = audioUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void uploadAudioToFirebase(View v) {
        if (textView.getText().toString().equals("no song selected")) {
            Toast.makeText(getApplicationContext(), "please select an image", Toast.LENGTH_LONG).show();
        } else {
            uploadefile();
        }
    }

    private void uploadefile() {
        if (audioUri != null) {
            String durationtxt;
            Toast.makeText(getApplicationContext(), "uploading please wait", Toast.LENGTH_LONG).show();
            StorageReference storageReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(audioUri));
            StorageReference imageStorageReference = mStorageRef.child( System.currentTimeMillis() + "." + getFileExtension(imageUri));
            int durationInMillis = findsongDuration(audioUri);
            if (durationInMillis == 0) {
                durationtxt = "NA";
            }
            durationtxt = getDurationFromMilli(durationInMillis);
//            imageStorageReference.putFile(imageUri);


            mUploadTask=storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageStorageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri audiouri) {
                                    imageStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri imageuri) {
                                            dataModel dataModelobj1=new dataModel(editText.getText().toString(), audiouri.toString(), imageuri.toString());
                                            String uploadId1 = referencesong.push().getKey();
                                            referencesong.child(uploadId1).setValue(dataModelobj1);
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });


//            mUploadTask = storageReference.putFile(audioUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            dataModel dataModelobj = new dataModel(editText.getText().toString(), audioUri.toString(),imageUri.toString());
//                            String uploadId = referencesong.push().getKey();
//                            referencesong.child(uploadId).setValue(dataModelobj);
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(getApplicationContext(), "upload successful", Toast.LENGTH_LONG).show();
//                        }
//                    });
        } else {
            Toast.makeText(getApplicationContext(), "something is wrong", Toast.LENGTH_LONG).show();
        }
    }

    private String getDurationFromMilli(int durationInMillis) {
        Date date = new Date(durationInMillis);
        SimpleDateFormat simple = new SimpleDateFormat("mm:ss", Locale.getDefault());
        String myTime = simple.format(date);
        return myTime;
    }

    private int findsongDuration(Uri audioUri) {
        int timeInMillisec = 0;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(this, audioUri);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            timeInMillisec = Integer.parseInt(time);
            retriever.release();
            return timeInMillisec;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getFileExtension(Uri audioUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }
}