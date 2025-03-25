package com.omgservers.schema.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MessageBodyDto {

    @JsonIgnore
    public abstract MessageQualifierEnum getQualifier();
}
