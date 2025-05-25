package org.example;

import org.example.GUI.Window;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length == 2) {
            new Window(new File(args[0]), new File(args[1]));
        }
        else new Window();
    }
}
