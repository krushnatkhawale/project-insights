package com.example;

import java.io.File;

import static java.lang.String.format;

public class Dependency {

    public static final String REMOTE = "files-2.1\\";
    public static final String LOCAL = "repository\\";
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

    public static Dependency toDependecy(File file) {

        String trimmedPath = getTrimmedPath(file);

        File versionDir = file.getParentFile().getParentFile();
        String version = versionDir.getName();
        String artifact = versionDir.getParentFile().getName();
        String group = versionDir.getParentFile().getParentFile().getName();

        return new Dependency(group, artifact, version);
    }

    private static String getTrimmedPath(File file) {
        String path = file.toPath().toString();
        if (file.getPath().contains(REMOTE)) {
            return path.substring(path.indexOf(REMOTE) + REMOTE.length() + 1);
        } else {
            return path.substring(path.indexOf(LOCAL) + LOCAL.length() + 1);
        }
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
