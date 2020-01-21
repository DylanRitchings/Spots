package com.dylanritchings.IOTools;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    public static final String DB_URL = "https://spotsandroid.000webhostapp.com/connect/";
    public static final String GETSPOTS_URL = DB_URL + "get_spots.php";
    private Context myContext;
    public RequestQueue requestQueue;
    private static NetworkManager instance = null;
    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //other stuf if you need
    }

    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void getSpots(Object param1, final ListenerTool.SomeCustomListener<String> listener) {
        Map<String, Object> jsonParams = new HashMap<>();
        jsonParams.put("param1", param1);
        //final ArrayList<Spot> spots = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETSPOTS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.getResult(response);
//                try {
//                    JSONArray array = new JSONArray(response);
//
//                    for (int i = 0; i < array.length(); i++) {
//                        float diff = 4;
//                        float host = 3;
//
//                        JSONObject ob = array.getJSONObject(i);
//                        Spot spot = new Spot(Integer.parseInt(ob.getString("spotId"))
//                                , ob.getString("userId")
//                                , ob.getString("desc")
//                                , Float.parseFloat(ob.getString("lat"))
//                                , Float.parseFloat(ob.getString("lng"))
//                                , ob.getString("type")
//                                , diff
//                                , host);
//                        spots.add(spot);
//                        onCallBack.onTaskSuccess(spots);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
