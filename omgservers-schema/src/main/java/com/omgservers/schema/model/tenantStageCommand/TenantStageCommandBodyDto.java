package com.omgservers.schema.model.tenantStageCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class TenantStageCommandBodyDto {

    @JsonIgnore
    public abstract TenantStageCommandQualifierEnum getQualifier();
}
