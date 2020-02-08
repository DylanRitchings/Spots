package com.dylanritchings.IOTools;

import android.content.Context;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DBSelect {
    public static final String DB_URL = "https://spotsandroid.000webhostapp.com/connect/";
    public static final String GETSPOTS_URL = DB_URL + "get_spots.php";
    public static final String GETRATINGS_URL = DB_URL + "get_ratings.php";
    private Context myContext;
    public RequestQueue requestQueue;
    private static DBSelect instance = null;
    private DBSelect(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        myContext = context;
    }

    public static synchronized DBSelect getInstance(Context context)
    {
        if (null == instance)
            instance = new DBSelect(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized DBSelect getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(DBSelect.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void getRatings(final String spotIdInput, final ListenerTool.SomeCustomListener<String> listener){
//        Map<String, Object> jsonParams = new HashMap<>();
//        jsonParams.put("param1", param1);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, GETRATINGS_URL + "?spotId=" + spotIdInput, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.getResult(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != error.networkResponse)
                {
                    Log.d("ERROR" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                    listener.getResult(null);
                }
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
//                Log.d("TEST",spotId);
//                params.put("spotId", spotId);

                return params;
            }
        };

        //requestQueue.add(stringRequest);
        MySingleton.getInstance(myContext).addToRequestQueue(stringRequest);
    }


    public void getSpots(Object param1, final ListenerTool.SomeCustomListener<String> listener) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("param1", param1);
        //final ArrayList<Spot> spots = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETSPOTS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.getResult(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != error.networkResponse)
                {
                    Log.d("ERROR" + ": ", "Error Response code: " + error.networkResponse.statusCode);
                    listener.getResult(null);
                }
            }
        });
        requestQueue.add(stringRequest);
    }
//    public interface CallBack {
//        void onTaskSuccess(ArrayList<Spot> spots);
//
//        void onFail(String msg);
//    }
//    public ArrayList<Spot> getSpotsArray(){
//        getSpots(new CallBack() {
//            @Override
//            public void onTaskSuccess(ArrayList<Spot> spots) {
//
//            }
//
//            @Override
//            public void onFail(String msg) {
//            }
//        });
//        return spots;
//    }
}
