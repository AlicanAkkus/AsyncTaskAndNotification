package com.example.backgroundtask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ProgressDialog progressDialog;
	private NotificationManager notificationManager;
	private Notification notification;

	final CharSequence ilkMesaj = "Ýndirme baþladý...";
	final int ikon = R.drawable.download_icon;
	final long gorulmeZamani = System.currentTimeMillis();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button btnStart = (Button) findViewById(R.id.button1);

		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub
				new BackGroundTask().execute(((Void) null));
			}
		});
	}

	public class BackGroundTask extends AsyncTask<Void, Integer, Void> {

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			notification = new Notification(ikon, ilkMesaj, gorulmeZamani);
			notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			notification.setLatestEventInfo(getApplicationContext(),
					"Ýndirme..", "Dosyalar indiriliyor..", null);
			notificationManager.notify(1, notification);
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMax(100);
			progressDialog.setProgress(0);
			progressDialog.setTitle("Yükleniyor...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(final Void... params) {
			// TODO Auto-generated method stub
			for (int i = 0; i < 101; i = (int) (i + (Math.random() * 10 + 1))) {
				try {
					publishProgress(i);
					Thread.sleep(1000);
				} catch (final InterruptedException e) {

				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			notificationManager.cancel(1);
			Toast.makeText(getApplicationContext(), "Ýndirme tamamlandý..",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onProgressUpdate(final Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			final Integer current = values[0];
			progressDialog.setProgress(current);
		}

		@SuppressLint("NewApi")
		@Override
		protected void onCancelled(final Void result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
			progressDialog.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
