package com.dylanritchings.IOTools;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import com.dylanritchings.Activities.UploadSpotActivity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class InsertData extends AsyncTask<String, Void, String> {
	Context ctx;
	public InsertData(){
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	//public void UploadSpotInput(final String userIdInput, final String descInput, final float latInput, final float lngInput, final String typeInput) {

	//	class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		//String reg_url = "https://spots-android.gearhostpreview.com/upload_spot.php";
		String reg_url = "https://80.5.205.113/spots/upload_spot.php";
		String method = params[0];
		if (method.equals("uploadSpot")) {
			String userId = params[1];
			String desc = params[2];
			String lat = params[3];
			String lng = params[4];
			String type = params[5];
			String difficulty = params[6];
			String hostility = params[7];
			try {
				URL url = new URL(reg_url);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setDoOutput(true);
				//httpURLConnection.setDoInput(true);
				OutputStream OS = httpURLConnection.getOutputStream();
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
				String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
						URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
						URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&" +
						URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8") + "&" +
						URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")  + "&" +
						URLEncoder.encode("difficulty", "UTF-8") + "=" + URLEncoder.encode(difficulty, "UTF-8") + "&" +
						URLEncoder.encode("hostility", "UTF-8") + "=" + URLEncoder.encode(hostility, "UTF-8");
				bufferedWriter.write(data);
				bufferedWriter.flush();
				bufferedWriter.close();
				OS.close();
				InputStream IS = httpURLConnection.getInputStream();
				IS.close();
				//httpURLConnection.connect();
				httpURLConnection.disconnect();
				return "Registration Success...";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		//Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
	}
}


