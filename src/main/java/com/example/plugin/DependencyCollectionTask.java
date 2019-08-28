package com.example.plugin;

import com.example.model.Dependency;
import com.example.model.Info;
import com.example.util.Utils;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class DependencyCollectionTask extends DefaultTask {

    private Project project = getProject();

    @Input
    String targetUrl;

    @Input
    String appName;

    @Input
    boolean disablePost;

    @TaskAction
    void taskAction() {

        Info projectInfo = getProjectInfo();

        projectInfo.print();

        post(projectInfo);
    }

    private void post(Info projectInfo) {

        if (disablePost) {
            System.out.println("  Dependency posting disabled!");
            return;
        }

        Utils.post(projectInfo);
    }

    private Info getProjectInfo() {

        String group = project.getGroup().toString();
        String appName = ofNullable(this.appName).orElse(project.getName());
        List<Dependency> collect = getDependencies(project);

        String host = ofNullable(targetUrl).orElse("defaultHost:8080//path");

        Info info = new Info();
        info.setGroupName(group);
        info.setAppName(appName);
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