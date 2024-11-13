package com.omgservers.schema.model.outgoingCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class OutgoingCommandBodyDto {

    @JsonIgnore
    public abstract OutgoingCommandQualifierEnum getQualifier();
}
