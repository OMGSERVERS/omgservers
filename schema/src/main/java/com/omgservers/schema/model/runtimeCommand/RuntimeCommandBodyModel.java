package com.omgservers.schema.model.runtimeCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class RuntimeCommandBodyModel {

    @JsonIgnore
    public abstract RuntimeCommandQualifierEnum getQualifier();
}
