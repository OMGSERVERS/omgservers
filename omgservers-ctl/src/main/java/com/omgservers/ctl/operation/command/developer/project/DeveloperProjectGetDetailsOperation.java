package com.omgservers.ctl.operation.command.developer.project;

public interface DeveloperProjectGetDetailsOperation {

    void execute(String tenant, String project, String service, String user, boolean prettyPrint);
}
