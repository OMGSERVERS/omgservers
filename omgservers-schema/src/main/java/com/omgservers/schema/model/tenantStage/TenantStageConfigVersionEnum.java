package com.omgservers.schema.model.tenantStage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantStageConfigVersionEnum {
    V1(1);

    final int version;
}
