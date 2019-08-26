package com.example;

import org.gradle.api.Action;

import static java.util.Arrays.asList;

public class DefaultAction implements Action<DependencyCollectionTask> {

    @Override
    public void execute(DependencyCollectionTask task) {
        task.setAppName("DefaultAppName");
        task.setGroupName("com.default.group");
        task.setDependencies(asList());
    }
}