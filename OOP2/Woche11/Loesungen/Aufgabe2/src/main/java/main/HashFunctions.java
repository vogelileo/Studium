package main;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class HashFunctions {

    //TODO: Implement Integer-Cast
    public static int hashCode1(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_16);
        ByteBuffer wrapped = ByteBuffer.wrap(b);
        return wrapped.getInt();

    }

    //TODO: Implement Komponentensumme
    public static int hashCode2(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash += s.charAt(i);
        }
        return hash;
    }

    //TODO: Implement Polynom-Akkumulation
    public static int hashCode3(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash +=  s.charAt(i) * Math.pow(31,(s.length() - i + 1));
        }
        return hash;
    }

    //TODO: Eigene Hash-Funktion
    //Modular hashing
    public static int hashCode4(String s) {
        int hash = 0;
        int R = 31;
        int M = 7919;
        for (int i = 0; i < s.length(); i++) {
            hash = (R * hash + s.charAt(i)) % M;
        }
        return hash;
    }
}
