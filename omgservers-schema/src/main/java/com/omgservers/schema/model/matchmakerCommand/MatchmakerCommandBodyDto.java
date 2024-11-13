package com.omgservers.schema.model.matchmakerCommand;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MatchmakerCommandBodyDto {

    @JsonIgnore
    public abstract MatchmakerCommandQualifierEnum getQualifier();
}
