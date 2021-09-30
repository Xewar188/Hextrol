package main;

import window.*;

public class Main {

    private static Window w;
    public static void main(String[] args) {
       w = new Window();
    }
    public static void close() {
        w.dispose();
    }

}
