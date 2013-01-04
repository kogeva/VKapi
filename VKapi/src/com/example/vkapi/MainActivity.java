package com.example.vkapi;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView text = (TextView) findViewById(R.id.myText);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		new AsyncTask<Void, Void, JSONObject>() {

			@Override
			protected JSONObject doInBackground(Void... params) {
				
				VKapi api = new VKapi("vovan5@yandex.ru", "vova19898");

				try {
					JSONObject  obj = new JSONObject(api.searchAudio("Sam And The Womp!"));
					JSONArray array = new JSONArray(obj.getString("response"));
					return array.getJSONObject(1);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(JSONObject result){
				super.onPostExecute(result);
				try {
					mediaPlayer.setDataSource(result.getString("url"));
					mediaPlayer.prepare();
					mediaPlayer.start();
					text.setText(result.getString("title"));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute();				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
