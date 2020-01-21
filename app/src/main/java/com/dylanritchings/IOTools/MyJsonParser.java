package com.dylanritchings.IOTools;

import java.util.ArrayList;
import java.util.List;

public class MyJsonParser
{
    //... other stuff
    public static <T> List<T> getListObjects(String json_text, String json_path, Class<T> c)
    {
        Gson gson = new Gson();
        try
        {
            List<T> parsed_list = new ArrayList<>();
            List<Object> nodes = JsonPath.read(json_text, json_path);

            for (Object node : nodes)
            {
                parsed_list.add(gson.fromJson(node.toString(), c));
            }
            return (parsed_list);
        }
        catch (Exception e)
        {
            return (new ArrayList<>());
        }
    }
    //... other stuff
}