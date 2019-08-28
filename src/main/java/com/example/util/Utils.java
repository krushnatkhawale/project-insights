package com.example.util;

import com.example.model.Info;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    public static void post(Info project) {
        try {
            String infoString = project.toInfoString();
            URL url = new URL(project.getHost());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = infoString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        } catch (Exception e) {
            System.out.println("Error while posting dependecies: " + e.getMessage());
        }
    }
}
