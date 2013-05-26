package com.vj.android;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class DisplayPhotoActivity extends Activity implements ViewFactory {

	//private List<Uri> listImages = new ArrayList<Uri>();
	private List<Uri> listThumbnails = new ArrayList<Uri>();

	private ImageSwitcher imageSwitcher1;

	private ImageSwitcher imageSwitcher2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_photo);

		loadImageList();

		imageSwitcher1 = (ImageSwitcher) findViewById(R.id.switcher1);
		Gallery gallery1 = (Gallery) findViewById(R.id.gallery1);
		setupImageSwitcher(imageSwitcher1, gallery1);

		// imageSwitcher2 = (ImageSwitcher) findViewById(R.id.switcher2);
		// Gallery gallery2 = (Gallery) findViewById(R.id.gallery2);
		// setupImageSwitcher(imageSwitcher2, gallery2);

	}

	private void setupImageSwitcher(final ImageSwitcher imageSwitcher, final Gallery gallery) {
		imageSwitcher.setFactory(this);
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		// imageSwitcher.setImageURI(listImages.get(0));
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				ImageView view = (ImageView) imageSwitcher.getCurrentView();
				BitmapDrawable bd = (BitmapDrawable) view.getDrawable();
				if (bd != null) {
					Bitmap b = bd.getBitmap();
					b.recycle();
				}
				// imageSwitcher.setImageDrawable(new BitmapDrawable(null,
				// BitmapFactory.decodeFile(listImages.get(position).getPath())));
				
				Drawable image = imageOperations(listThumbnails.get(position).getPath());
				imageSwitcher.setImageDrawable(image);
			}
		});
	}
	public Object fetch(String address) throws MalformedURLException, IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
	private Drawable imageOperations(String url) {
		try {
			InputStream is = (InputStream)fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	private void loadImageList() {
		File dir = new File(Constant.DIRECTORY);
		File files[] = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename.startsWith(Constant.PIC_PREFIX)) {
					return true;
				}
				return false;
			}
		});
		for (File file : files) {
			Uri uri = Uri.fromFile(file);
			//listImages.add(uri);
			String thumb = uri.getPath().replaceFirst(Constant.PIC_PREFIX, Constant.THUMB_PIC_PREFIX );
			listThumbnails.add(Uri.parse(thumb));
		}
	}

	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(0xFF000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;

		public ImageAdapter(Context c) {
			context = c;

			// ---setting the style---
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
			itemBackground = a.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
			a.recycle();
		}

		// ---returns the number of images---
		public int getCount() {
			return listThumbnails.size();
		}

		// ---returns the ID of an item---
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		// ---returns an ImageView view---
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView picturesView = null;
			if (convertView == null) {
				picturesView = new ImageView(context);
				picturesView.setImageURI(listThumbnails.get(position));
				picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				picturesView.setLayoutParams(new Gallery.LayoutParams(150, 120));
				picturesView.setBackgroundResource(itemBackground);
			} else {
				picturesView = (ImageView) convertView;
			}
			return picturesView;
		}
	}
}