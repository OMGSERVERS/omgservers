package com.omgservers.service.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EventBodyModel {

    @JsonIgnore
    public abstract EventQualifierEnum getQualifier();
}
