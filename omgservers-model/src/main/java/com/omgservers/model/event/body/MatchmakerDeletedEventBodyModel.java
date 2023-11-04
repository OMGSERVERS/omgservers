package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.matchmaker.MatchmakerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerDeletedEventBodyModel extends EventBodyModel {

    @NotNull
    MatchmakerModel matchmaker;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_DELETED;
    }

    @Override
    public Long getGroupId() {
        return matchmaker.getId();
    }
}