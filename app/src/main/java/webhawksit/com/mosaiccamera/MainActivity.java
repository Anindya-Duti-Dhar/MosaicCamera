package webhawksit.com.mosaiccamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int MULTIPLE_PERMISSIONS = 10;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    Button mGoLive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoLive = (Button) findViewById(R.id.getPermission);
        mGoLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    //Do not need to check the permission
                    recordVideo();
                } else {
                    if (checkPermissions()) {
                        // permissions granted.
                        recordVideo();
                        Toast.makeText(getApplicationContext(), "Previously Granted", Toast.LENGTH_LONG).show();
                    } else {
                        // show dialog informing them that we lack certain permissions
                    }
                }
            }
        });
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                    recordVideo();
                    Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_LONG).show();
                } else {
                    // no permissions granted.
                }
                return;
            }
        }
    }

    public void recordVideo(){
        finish();
        Intent intent = new Intent(getApplicationContext(), VideoCapture.class);
        startActivity(intent);
    }



    /*        // The Surface has been created, now tell the camera where to draw the preview.
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setRecordingHint(true);
        parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
        Camera.Size size = getBestPreviewSize(parameters);
        mCamera.setParameters(parameters);

        //resize the view to the specified surface view width in layout
        int newHeight = size.height / (size.width / mCameraContainerWidth);
        mSurfaceView.getLayoutParams().height = newHeight;*/

}