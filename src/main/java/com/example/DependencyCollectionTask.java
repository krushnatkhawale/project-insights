package com.example;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class DependencyCollectionTask extends DefaultTask {

    Project project = getProject();

    @Input
    String targetUrl;

    @TaskAction
    void taskAction() {
        System.out.println("  Task is being executed!");

        Info projectInfo = getProjectInfo();
        projectInfo.print();

        post(projectInfo);
    }

    private void post(Info projectInfo) {
        try {
            String infoString = projectInfo.toInfoString();
            URL url = new URL(projectInfo.getHost());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = infoString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("  DONE");
    }

    private Info getProjectInfo() {

        String group = project.getGroup().toString();
        String applicationName = project.getName();
        List<Dependency> collect = getDependencies(project);

        String host = ofNullable(targetUrl).orElse("defaultHost:8080//path");

        Info info = new Info();
        info.setGroupName(group);
        info.setAppName(applicationName);
        info.setDependencies(collect);
        info.setHost(host);
        return info;
    }

    private List<Dependency> getDependencies(Project project) {
        return project.getConfigurations()
                .stream()
                .filter(config -> config.getName().contains("compile"))
                .map(Configuration::resolve)
                .flatMap(Collection::stream)
                .map(Dependency::toDependency)
                .sorted((Comparator.comparing(Dependency::getGroup)))
                .collect(Collectors.toList());
    }
}