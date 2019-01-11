package com.example.fabiomoscariello.recyclerviewprova;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fabiomoscariello.recyclerviewprova.Model.Animale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView listaAnimaliView;
    private EditText animaleEditText;
    private Button inserisciAnimaleButton;
    private Button scegliImmagineButton;
    private ArrayList<Animale> listaAnimali;
    private AnimaleAdapter adapter;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_IMAGE = 100;
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;
    private static final List<String> sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );
    private int PermissionRequestCount;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAnimali = new ArrayList<Animale>();
        listaAnimali.add(new Animale("Cane"));
        listaAnimali.add(new Animale("Gatto"));
        adapter=new AnimaleAdapter(listaAnimali);
        listaAnimaliView = findViewById(R.id.listaAnimaliView_id);
        listaAnimaliView.setHasFixedSize(true);
        listaAnimaliView.setLayoutManager(new LinearLayoutManager(this));
        listaAnimaliView.setAdapter(adapter);
        animaleEditText = findViewById(R.id.animaleEditText_id);
        inserisciAnimaleButton = findViewById(R.id.inserisciAnimaleButton_id);
        scegliImmagineButton = findViewById(R.id.scegliImmagine_id);
        inserisciAnimaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,""+uri.toString());
                Log.d(TAG,""+listaAnimali.size());
                if(uri.equals(null)){
                    listaAnimali.add(new Animale(animaleEditText.getText().toString()));
                }
                listaAnimali.add(new Animale(animaleEditText.getText().toString(),uri));
                Log.d(TAG,""+listaAnimali.size());
                adapter.setDataSet(listaAnimali);
                listaAnimaliView.setAdapter(adapter);
            }
        });
        scegliImmagineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE);
            }
        });
    }

    private boolean checkAllPermissions() {
        Log.d(TAG, TAG + "checkAllPermissions");
        boolean hasPermissions = true;
        for (String permission : sPermissions) {
            hasPermissions &=
                    ContextCompat.checkSelfPermission(
                            this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    //richiede i permessi
    private void requestPermissionsIfNecessary() {
        Log.d(TAG, TAG + "requestPermissionsifNecessary");
        if (!checkAllPermissions()) {
            if (PermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                PermissionRequestCount += 1;
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toArray(new String[0]),
                        REQUEST_CODE_PERMISSIONS);
            } else {
                findViewById(R.id.scegliImmagine_id).setEnabled(false);
            }
        }
    }

    //Controlla i permessi se ne manca qualcuno
    @Override
    public void onRequestPermissionsResult(

            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        Log.d(TAG, TAG + "onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary(); // no-op if permissions are granted already.
        }
    }

    private Uri handleImageRequestResult(Intent data) {
        uri = null;
        if (data.getClipData() != null) {
            uri = data.getClipData().getItemAt(0).getUri();
        } else if (data.getData() != null) {
            uri = data.getData();
        }

        if (uri == null) {
            //Log.e(TAG, "Invalid input image uri.");
            return null;
        }
        return uri;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, TAG + "onActivityResult");
        Log.d(TAG, "requestCode" + requestCode);
        Log.d(TAG, "resultCode" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE:
                    Log.d(TAG, "requestCodeImage" + REQUEST_CODE_IMAGE);
                    handleImageRequestResult(data);
                    break;
                default:
                    Log.d(TAG, "requestCode sconosciuto" + requestCode);
            }
        } else {
            Log.e(TAG, String.format("Unexpected Result code %s", resultCode));
        }
    }
}

