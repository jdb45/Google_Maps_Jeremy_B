package com.jeremy;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) throws Exception {


	String key = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("key.txt"))) {
            key = reader.readLine();
            System.out.println(key);


        } catch (Exception ioe){
            System.out.println("No key file found, or could not read key. Please verify key.txt present");
            System.exit(-1);

    }


    }

}
