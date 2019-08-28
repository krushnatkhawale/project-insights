package com.example;

import java.util.List;

import static java.lang.String.format;

public class Info {

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

    public String toInfoString() {
        return format(INFO_STRING, appName, groupName, dependencies.size());
    }

    public void print() {
        System.out.println(toInfoString());
    }

    private static final String INFO_STRING = "  App: %s\n" +
            "  Group: %s\n" +
            "  No of dependencies: %s\n";

}