package inpt.sud.instatram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseCommonRegistrar;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Ecran3 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ImageView imageView;
    Button buttonshare;
    Uri imageUri;
    String myURL = "";
    StorageTask uploadTask;
    StorageReference storageReference;
    DatabaseReference databaseRef;
    EditText title;
    String GetImageNameFromEditText;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static Context context;
    Upload upload;
    Bitmap bitmap;
    String station = new String();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //CHECK MODE
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Darck);
        } else {
            setTheme(R.style.Theme_Light);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran3);

        Bundle extras1 = getIntent().getExtras();
        station = extras1.getString("STATION_NAME1");


        title = findViewById(R.id.editText);
        storageReference = FirebaseStorage.getInstance().getReference("Station");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        buttonshare = findViewById(R.id.share);






        imageView = findViewById(R.id.imageView);

        buttonshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageNameFromEditText = title.getText().toString();
                uploadImage();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bitmap = (Bitmap) extras.get("image");
            imageView.setImageBitmap(bitmap);
            imageView.setImageURI(imageUri);
            Ecran3.context = getApplicationContext();
            imageUri= getImageUri(context,bitmap);
        }
    }
    

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    private void uploadImage() {


        String titleTest = title.getText().toString();
        String imageTest = imageUri.getPath();

        if (imageUri != null) {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Posting");
            progressDialog.show();

            upload = new Upload(titleTest,imageTest,strDate);
            StorageReference filereference = FirebaseStorage.getInstance().getReference(station).child(station).child(System.currentTimeMillis() + "." + getFileExtension(imageUri));


            uploadTask = filereference.putFile(imageUri);
            upload = new Upload();
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isComplete()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myURL = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(station);
                        String stationid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("stationid", stationid);
                        hashMap.put("title", title.getText().toString());
                        hashMap.put("imageUrl", myURL);
                        hashMap.put("date",strDate);


                        reference.child(stationid).setValue(hashMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(Ecran3.this, Ecran2.class));
                        finish();


                    } else {
                        Toast.makeText(Ecran3.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Ecran3.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "no image selected", Toast.LENGTH_SHORT).show();

        }
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, Ecran2.class);
        startActivity(intent);
    }





    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new HomeFragment())
                        .commit();
                return true;

            case R.id.item_map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new MapFragment())
                        .commit();
                return true;
            case R.id.item_setting:
                Intent intent = new Intent(this, ModeConfig.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    public void GoEcran2 (View v){
        Intent intent = new Intent(this, Ecran2.class);
        startActivity(intent);
    }



}





