package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchDeletedEventBodyModel extends EventBodyModel {

    Long matchmakerId;
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_DELETED;
    }

    @Override
    public Long getGroupId() {
        return matchmakerId;
    }
}
