package com.dylanritchings.IOTools;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dylanritchings.Models.Spot;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetData {
    public static final String DB_URL = "https://spotsandroid.000webhostapp.com/connect/";
    public static final String GETSPOTS_URL = DB_URL + "get_spots.php";
    private Context myContext;
    final ArrayList<Spot> spots = new ArrayList<>();
    public GetData(Context context)
    {
        myContext = context;
    }
    public void getSpots(final CallBack onCallBack) {
        //final ArrayList<Spot> spots = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GETSPOTS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        float diff = 4;
                        float host = 3;

                        JSONObject ob = array.getJSONObject(i);
                        Spot spot = new Spot(Integer.parseInt(ob.getString("spotId"))
                                , ob.getString("userId")
                                , ob.getString("desc")
                                , Float.parseFloat(ob.getString("lat"))
                                , Float.parseFloat(ob.getString("lng"))
                                , ob.getString("type")
                                , diff
                                , host);
                        spots.add(spot);
                        onCallBack.onTaskSuccess(spots);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onCallBack.onFail(error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public interface CallBack {
        void onTaskSuccess(ArrayList<Spot> spots);

        void onFail(String msg);
    }
    public ArrayList<Spot> getSpotsArray(){
        getSpots(new CallBack() {
            @Override
            public void onTaskSuccess(ArrayList<Spot> spots) {

            }

            @Override
            public void onFail(String msg) {
            }
        });
        return spots;
    }
}
