package com.example;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.tasks.TaskAction;

import java.util.List;

public class DependencyCollectionTask extends DefaultTask {

    String appName;
    String groupName;
    List<Dependency> dependencies;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }


    @TaskAction
    void postInfo() {
        System.out.println("Posting dependency info");
        System.out.println("Application name: " + appName);
        System.out.println("Group: " + groupName);
        System.out.println("Posting to: url");
    }
}