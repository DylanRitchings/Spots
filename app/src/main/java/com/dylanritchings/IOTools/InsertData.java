package com.dylanritchings.IOTools;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//public class InsertData extends AsyncTask<String, Void, String> {
public class InsertData{
	//Context ctx;
	public static final String DB_URL = "https://spotsandroid.000webhostapp.com/connect/";
	public static final String UPLOADSPOT_URL = DB_URL + "upload_spot.php";
	public static final String UPLOADRATING_URL = DB_URL + "upload_rating.php";
	private Context myContext;
	public InsertData(Context context)
	{
		myContext = context;
	}

	public void UploadSpot(final String userId, final String desc, final String lat, final String lng, final String type, final String diff, final String host){

		//RequestQueue requestQueue = Volley.newRequestQueue(myContext);


		// Creating string request with post method.
		StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOADSPOT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String ServerResponse) {

						// Showing response message coming from server.
						Toast.makeText(myContext.getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						// Showing error message if something goes wrong.
						Toast.makeText(myContext.getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {

				// Creating Map String Params.
				Map<String, String> params = new HashMap<String, String>();

				// Adding All values to Params.
				params.put("userId", userId);
				params.put("desc", desc);
				params.put("lat", lat);
				params.put("lng", lng);
				params.put("type", type);
				params.put("difficulty", diff);
				params.put("hostility", host);

				return params;
			}

		};
		MySingleton.getInstance(myContext).addToRequestQueue(stringRequest);
		// Creating RequestQueue.
		//RequestQueue requestQueue = Volley.newRequestQueue(myContext);

// Adding the StringRequest object into requestQueue.
		//requestQueue.add(stringRequest);
	}


//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//	}
//
//	//public void UploadSpotInput(final String userIdInput, final String descInput, final float latInput, final float lngInput, final String typeInput) {
//
//	//	class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
//	@Override
//	protected String doInBackground(String... params) {
//		//String reg_url = "https://spots-android.gearhostpreview.com/upload_spot.php";
//		String reg_url = "https://spotsandroid.000webhostapp.com/connect/upload_spot.php";
//		String method = params[0];
//		if (method.equals("uploadSpot")) {
//			String userId = params[1];
//			String desc = params[2];
//			String lat = params[3];
//			String lng = params[4];
//			String type = params[5];
//			String difficulty = params[6];
//			String hostility = params[7];
//			try {
//				URL url = new URL(reg_url);
//				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//				httpURLConnection.setRequestMethod("POST");
//				httpURLConnection.setDoOutput(true);
//				//httpURLConnection.setDoInput(true);
//				OutputStream OS = httpURLConnection.getOutputStream();
//				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
//				String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") + "&" +
//						URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
//						URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8") + "&" +
//						URLEncoder.encode("lng", "UTF-8") + "=" + URLEncoder.encode(lng, "UTF-8") + "&" +
//						URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")  + "&" +
//						URLEncoder.encode("difficulty", "UTF-8") + "=" + URLEncoder.encode(difficulty, "UTF-8") + "&" +
//						URLEncoder.encode("hostility", "UTF-8") + "=" + URLEncoder.encode(hostility, "UTF-8");
//				bufferedWriter.write(data);
//				bufferedWriter.flush();
//				bufferedWriter.close();
//				OS.close();
//				InputStream IS = httpURLConnection.getInputStream();
//				IS.close();
//				//httpURLConnection.connect();
//				httpURLConnection.disconnect();
//				return "Registration Success...";
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//
//	}
//	@Override
//	protected void onProgressUpdate(Void... values) {
//		super.onProgressUpdate(values);
//	}
//
//	@Override
//	protected void onPostExecute(String result) {
//		//Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
//	}
}


