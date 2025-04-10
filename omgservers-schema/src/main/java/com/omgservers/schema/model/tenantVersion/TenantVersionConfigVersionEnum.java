package com.omgservers.schema.model.tenantVersion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantVersionConfigVersionEnum {
    V1(1);

    final int version;
}
