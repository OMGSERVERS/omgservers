package com.omgservers.schema.model.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantProjectConfigVersionEnum {
    V1(1);

    final int version;
}
