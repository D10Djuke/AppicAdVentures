package adventures.ad.appic.main.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import adventures.ad.appic.app.R;
import adventures.ad.appic.game.Player;
import adventures.ad.appic.main.custom.MessageBox;

public class CameraPreview extends Activity {
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera camera = null;
    private boolean inPreview = false;
    private boolean cameraConfigured = false;
    private boolean healthZero = true;

    private Player mPlayer;
    private AnimationDrawable testAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPlayer = (Player) intent.getParcelableExtra("mPlayer");
        setContentView(R.layout.activity_camerapreview);
        preview = (SurfaceView) findViewById(R.id.cpPreview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        ImageView animationImage = (ImageView) findViewById(R.id.animationView);
        animationImage.setBackgroundResource(R.drawable.animation_test);
        testAnimation = (AnimationDrawable) animationImage.getBackground();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            testAnimation.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();



        int cameraId = -1;

        for(int i = 0; i < Camera.getNumberOfCameras();i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                cameraId = i;
            }
        }
        camera = Camera.open(cameraId);
        startPreview();
    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera = null;
        inPreview = false;

        super.onPause();
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return (result);
    }

    private void initPreview(int width, int height) {
        if (camera != null && previewHolder.getSurface() != null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCallback",
                        "Exception in setPreviewDisplay()", t);
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = getBestPreviewSize(width, height,
                        parameters);

                if (size != null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured = true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera != null) {
            camera.startPreview();
            inPreview = true;
        }
    }

    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaanged()
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);

            Camera.Parameters parameters = camera.getParameters();
            Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

            if(display.getRotation() == Surface.ROTATION_0)
            {
                parameters.setPreviewSize(height, width);
                camera.setDisplayOrientation(90);
            }

            if(display.getRotation() == Surface.ROTATION_90)
            {
                parameters.setPreviewSize(width, height);
            }

            if(display.getRotation() == Surface.ROTATION_180)
            {
                parameters.setPreviewSize(height, width);
            }

            if(display.getRotation() == Surface.ROTATION_270)
            {
                parameters.setPreviewSize(width, height);
                camera.setDisplayOrientation(180);
            }

            camera.setParameters(parameters);

            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };

 /*   @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (healthZero) {
            healthZero = false;
            MessageBox messagebox = new MessageBox("YOU WIN!", "FLAWLESS VICTORY!", MessageBox.Type.VICTORY_BOX, this);
            messagebox.setPlayer(mPlayer);
            messagebox.popMessage();
        }

        return super.onTouchEvent(event);
    }*/

    public void win(View view){
            if (healthZero) {
                healthZero = false;
                MessageBox messagebox = new MessageBox("YOU WIN!", "Victory!", MessageBox.Type.VICTORY_BOX, this);
                messagebox.setPlayer(mPlayer);
                messagebox.popMessage();
            }
        }
}