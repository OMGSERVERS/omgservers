package com.omgservers.schema.model.tenantImage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantImageConfigVersionEnum {
    V1(1);

    final int version;
}
