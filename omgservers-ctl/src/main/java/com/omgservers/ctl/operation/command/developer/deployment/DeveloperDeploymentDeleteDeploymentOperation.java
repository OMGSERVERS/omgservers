package com.omgservers.ctl.operation.command.developer.deployment;

public interface DeveloperDeploymentDeleteDeploymentOperation {

    void execute(Long deploymentId,
                 String service,
                 String user);
}
