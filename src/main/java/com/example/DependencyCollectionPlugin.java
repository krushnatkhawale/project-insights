package com.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DependencyCollectionPlugin implements Plugin<Project> {

    public static final String TASK_NAME = "dependency-collection";
    private DefaultAction defaultAction = new DefaultAction();

    @Override
    public void apply(Project project) {
        project.getTasks()
                .create(TASK_NAME, DependencyCollectionTask.class, defaultAction);
    }
}