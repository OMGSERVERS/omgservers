package com.omgservers.schema.model.deployment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeploymentConfigVersionEnum {
    V1(1);

    final int version;
}
