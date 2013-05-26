package com.vj.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;

public class SavePhotoTask extends AsyncTask<byte[], String, String> {
	public static final String TAG = "SavePhotoTask";
	private static final int MAX_PICS_COUNT = 4;
	public static int counter = 0;

	@Override
	protected String doInBackground(byte[]... jpeg) {

		checkCounter();
		File pictureFile = new File(Constant.DIRECTORY + File.separator + Constant.PIC_PREFIX + ++counter + ".png");

		if (pictureFile.exists()) {
			pictureFile.delete();
		}

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			Bitmap bitmap1 = BitmapFactory.decodeByteArray(jpeg[0], 0, jpeg[0].length);
			bitmap1.compress(CompressFormat.JPEG, 100, fos);

			fos.write(jpeg[0]);
			fos.close();

			ExifInterface myEI = new ExifInterface(pictureFile.getAbsolutePath());
			byte[] thumbArray = myEI.getThumbnail();

			File thumbnail = new File(Constant.DIRECTORY, Constant.THUMB_PIC_PREFIX + counter + ".png");
			if (thumbnail.exists()) {
				thumbnail.delete();
			}

			Bitmap bitmap = BitmapFactory.decodeByteArray(thumbArray, 0, thumbArray.length);
			FileOutputStream fosThumb = new FileOutputStream(thumbnail);
			bitmap.compress(CompressFormat.JPEG, 20, fosThumb);
			fosThumb.write(thumbArray);
			fosThumb.close();

		} catch (FileNotFoundException e) {
			Log.d(TAG, "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Error accessing file: " + e.getMessage());
		}

		return (null);
	}

	private void checkCounter() {
		if (counter > MAX_PICS_COUNT) {
			counter = 0;
		}
	}
}
