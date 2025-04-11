package com.omgservers.schema.model.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantConfigVersionEnum {
    V1(1);

    final int version;
}
