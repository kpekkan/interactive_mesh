package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        File vertexFile = new File("src/test/java/text1/vertex.txt");
        File edgeFile = new File("src/test/java/text1/edge.txt");
        WireFrame2D wireFrame2D = new WireFrame2D();
        try {
            WireFrame2D.newWireFrame2DFromFile(wireFrame2D, vertexFile, edgeFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("hi");
    }
}