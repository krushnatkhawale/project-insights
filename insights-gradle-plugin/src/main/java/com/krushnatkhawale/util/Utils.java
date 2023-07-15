package com.krushnatkhawale.util;

import com.krushnatkhawale.model.Dependency;
import com.krushnatkhawale.model.Info;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class Utils {
    private static final Logger LOGGER = Logging.getLogger(Utils.class);
    public static final String REMOTE = "files-2.1\\";
    public static final String LOCAL = "repository\\";


    public static void post(Info project) {
        try {
            String infoString = project.toInfoString();
            URL url = new URL(project.getHost());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            project.getDependencies().forEach( d -> LOGGER.quiet("D: " + d.flattedString()) );

            LOGGER.quiet("      Application build info is being shared with monitoring tool ");

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = infoString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                LOGGER.quiet("        Thanks for helping to keep system up to date" + response.toString());
            } else {
                LOGGER.quiet("        POST request not worked, status code: " + responseCode);
            }
        } catch (Exception e) {
            LOGGER.quiet("       Error while posting dependencies: " + e.getMessage());
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
