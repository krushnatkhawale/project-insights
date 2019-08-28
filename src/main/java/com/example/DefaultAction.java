package com.example;

import org.gradle.api.Action;

public class DefaultAction implements Action<DependencyCollectionTask> {

    @Override
    public void execute(DependencyCollectionTask task) {
        System.out.println("  Default action is being executed!");
    }
}