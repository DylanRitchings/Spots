package com.dylanritchings.Utils;

import android.graphics.Color;

public class ColorCheck {
    public int getSpotColor(String type){
        int color = 0;
        switch (type) {
            case "Skatepark":
                color = Color.rgb(0, 127, 255);
                break;
            case "Stairs":
                color = Color.rgb(57, 255, 20);
                break;
            case "Handrail":
                color = Color.rgb(255, 0, 255);
                break;
            case "Box":
                color = Color.rgb(255, 127, 0);
                break;
            case "Gap":
                color = Color.rgb(127, 0, 255);
                break;
            case "Bank":
                color = Color.rgb(0, 0, 255);
                break;
            default:
                color = Color.rgb(255, 255, 0);
                break;
        }
        return color;
    }
}
