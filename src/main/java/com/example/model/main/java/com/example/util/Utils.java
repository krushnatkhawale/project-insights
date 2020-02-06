package com.example.model.main.java.com.example.util;

import com.example.model.main.java.com.example.model.Dependency;
import com.example.model.main.java.com.example.model.Info;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Utils {

    public static final String REMOTE = "files-2.1\\";
    public static final String LOCAL = "repository\\";


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

    public static Dependency toDependency(File file) {

        String trimmedPath = getTrimmedPath(file);
        String[] tokens = trimmedPath.split(Pattern.quote(File.separator));

        String group = tokens[0];
        String artifact = tokens[1];
        String version = tokens[2];

        return new Dependency(group, artifact, version);
    }

    private static String getTrimmedPath(File file) {
        String path = file.toPath().toString();
        if (file.getPath().contains(REMOTE)) {
            return path.substring(path.indexOf(REMOTE) + REMOTE.length());
        } else {
            return path.substring(path.indexOf(LOCAL) + LOCAL.length());
        }
    }
}
