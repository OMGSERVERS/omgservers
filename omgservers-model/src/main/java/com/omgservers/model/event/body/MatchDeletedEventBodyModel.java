package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.match.MatchModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    MatchModel match;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Long getGroupId() {
        return match.getId();
    }
}
