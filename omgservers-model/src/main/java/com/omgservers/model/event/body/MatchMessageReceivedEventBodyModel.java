package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchMessageReceivedEventBodyModel extends EventBodyModel {

    Long tenantId;
    Long stageId;
    Long userId;
    Long playerId;
    Long clientId;
    Long runtimeId;

    String text;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCH_MESSAGE_RECEIVED;
    }

    @Override
    public Long getGroupId() {
        return runtimeId;
    }
}
