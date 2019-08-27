package com.example;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskAction;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DependencyCollectionTask extends DefaultTask {

    @TaskAction
    void postInfo() {
        System.out.println("  Task is being executed!");

        Project project = getProject();

        System.out.println("  Group: " + getGroup());
        System.out.println("  Project: " + project);
        System.out.println("  Service: " + getServices());

        List<Dependency> collect = project.getConfigurations()
                .stream()
                .filter(config -> config.getName().contains("compile"))
                .map(Configuration::resolve)
                .flatMap(Collection::stream)
                .map(Dependency::toDependecy)
                .sorted((Comparator.comparing(Dependency::getGroup)))
                .collect(Collectors.toList());

        collect.forEach(cd -> {
            System.out.println("   \\ d: " + cd);
        });

        System.out.println("Test - 1");
    }
}