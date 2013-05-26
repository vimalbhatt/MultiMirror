package com.vj.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Activity displaying the camera and preview.
 * 
 * @author Vimal
 */

public class CameraActivity extends Activity implements CameraFragmentListener {
	public static final String TAG = "CameraActivity";

	/**
	 * On activity getting created.
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_camera);

	}

	/**
	 * On fragment notifying about a non-recoverable problem with the camera.
	 */
	@Override
	public void onCameraError() {
		Toast.makeText(this, "Error Camera Preview", Toast.LENGTH_SHORT).show();

		finish();
	}
	
}
