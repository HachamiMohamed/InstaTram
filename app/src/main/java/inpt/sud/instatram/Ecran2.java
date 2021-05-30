package inpt.sud.instatram;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;



import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import inpt.sud.instatram.R;

import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;


public class Ecran2 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    // on initialise la variable
    FloatingActionButton cameraButton;
    private static final int PERMISSION_CODE=1000;

    String name_station;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecran2);
        // On assigne la variable
        cameraButton = findViewById(R.id.camera);

        TextView textView= findViewById(R.id.station);
        Bundle extras = getIntent().getExtras();
        textView.setText(extras.getString("STATION_NAME"));
        name_station = extras.getString("STATION_NAME");


        //Request pour camera permission
        if (ContextCompat.checkSelfPermission(Ecran2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Ecran2.this, new String[]{Manifest.permission.CAMERA},
                    100);
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            //get capture
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(Ecran2.this,Ecran3.class);
            intent.putExtra("STATION_NAME1",name_station);
            intent.putExtra("image",captureImage);
            startActivity(intent);
        }


    }







    public void home(View v){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, new imageStation())
                .commit();
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.menu);
        popup.show();
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

    public void GoMainActivity(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}



