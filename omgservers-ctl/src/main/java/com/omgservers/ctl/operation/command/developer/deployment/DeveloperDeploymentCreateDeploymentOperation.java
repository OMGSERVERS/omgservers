package com.omgservers.ctl.operation.command.developer.deployment;

public interface DeveloperDeploymentCreateDeploymentOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String version,
                 String installation);
}
