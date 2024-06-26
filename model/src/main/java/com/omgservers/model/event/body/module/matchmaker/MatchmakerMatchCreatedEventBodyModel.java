package com.omgservers.model.event.body.module.matchmaker;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMatchCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_CREATED;
    }
}
