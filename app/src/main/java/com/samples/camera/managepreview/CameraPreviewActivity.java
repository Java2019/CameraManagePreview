package com.samples.camera.managepreview;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraPreviewActivity extends AppCompatActivity
        implements View.OnClickListener, SurfaceHolder.Callback{

    private SurfaceView surView;
    private Button bStart;
    private Button bStop;
    private SurfaceHolder holder;

    private Camera camera;

    boolean isCameraPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        surView = (SurfaceView)findViewById(R.id.surView);
        bStart = (Button)findViewById(R.id.bStart);
        bStop = (Button)findViewById(R.id.bStop);

        holder = surView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);

        bStop.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bStart:
                try {
                    camera = Camera.open();
                    camera.setPreviewDisplay(holder);
                    camera.startPreview();

                    isCameraPreview = true;

                    bStart.setEnabled(!isCameraPreview);
                    bStop.setEnabled(isCameraPreview);

                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bStop:
                camera.stopPreview();
                camera.release();

                isCameraPreview = false;

                bStart.setEnabled(!isCameraPreview);
                bStop.setEnabled(isCameraPreview);
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (isCameraPreview) {
            camera.stopPreview();
            camera.release();
            isCameraPreview = false;
        }
    }
}
