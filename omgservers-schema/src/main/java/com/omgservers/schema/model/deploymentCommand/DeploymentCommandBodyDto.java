package com.omgservers.schema.model.deploymentCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class DeploymentCommandBodyDto {

    @JsonIgnore
    public abstract DeploymentCommandQualifierEnum getQualifier();
}
