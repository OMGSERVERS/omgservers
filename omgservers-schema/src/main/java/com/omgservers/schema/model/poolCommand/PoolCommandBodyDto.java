package com.omgservers.schema.model.poolCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class PoolCommandBodyDto {

    @JsonIgnore
    public abstract PoolCommandQualifierEnum getQualifier();
}
