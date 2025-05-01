package com.omgservers.ctl.operation.command.developer.deployment;

public interface DeveloperDeploymentGetDetailsOperation {

    void execute(Long deploymentId,
                 String service,
                 String user,
                 boolean prettyPrint);
}
