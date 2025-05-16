package com.omgservers.schema.model.tenantDeploymentResource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantDeploymentResourceConfigVersionEnum {
    V1(1);

    final int version;
}
