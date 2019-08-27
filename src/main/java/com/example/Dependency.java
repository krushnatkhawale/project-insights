package com.example;

import java.io.File;

import static java.lang.String.format;

public class Dependency {

    private String group;
    private String artifact;
    private String version;

    public Dependency(String group, String name, String version) {
        this.group = group;
        this.artifact = name;
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static Dependency toDependecy(File dependency) {

        File versionDir = dependency.getParentFile().getParentFile();
        String version = versionDir.getName();
        String artifact = versionDir.getParentFile().getName();
        String group = versionDir.getParentFile().getParentFile().getName();

        return new Dependency(group, artifact, version);
    }

    @Override
    public String toString() {
        return format("{ group: %s, artifact: %s, version: %s }", group, artifact, version);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Dependency)) {
            return false;
        }

        Dependency dependency = (Dependency) o;

        return dependency.group.equals(group) &&
                dependency.artifact.equals(artifact) &&
                dependency.version.equals(version);
    }

}
