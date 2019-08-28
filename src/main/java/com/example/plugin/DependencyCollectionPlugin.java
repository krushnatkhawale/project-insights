package com.example.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class DependencyCollectionPlugin implements Plugin<Project> {

    public static final String TASK_NAME = "dependencyCollection";
    public static final String BUILD_TASK = "build";
    private DefaultAction defaultAction = new DefaultAction();

    @Override
    public void apply(Project project) {

        Task buildTask = project.getTasks()
                .findByName(BUILD_TASK);

        DependencyCollectionTask thisTask = project.getTasks()
                .create(TASK_NAME, DependencyCollectionTask.class, defaultAction);

        buildTask.dependsOn(thisTask);

    }
}