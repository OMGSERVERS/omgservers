package com.omgservers.schema.event.body.module.matchmaker;

import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMatchRuntimeRefDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_RUNTIME_REF_DELETED;
    }
}
