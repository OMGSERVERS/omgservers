package com.omgservers.ctl.dto.log;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class LogLineBodyDto {

    @JsonIgnore
    public abstract LogLineQualifierEnum getQualifier();
}
