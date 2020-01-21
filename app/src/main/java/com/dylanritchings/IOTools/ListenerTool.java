package com.dylanritchings.IOTools;

public class ListenerTool {
    public interface SomeCustomListener<T>
    {
        public void getResult(T object);
    }
}
