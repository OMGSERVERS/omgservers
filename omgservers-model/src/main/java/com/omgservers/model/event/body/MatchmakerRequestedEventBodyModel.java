package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerRequestedEventBodyModel extends EventBodyModel {

    Long tenantId;
    Long stageId;
    Long userId;
    Long playerId;
    Long clientId;
    String mode;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_REQUESTED;
    }

    @Override
    public Long getGroupId() {
        return clientId;
    }
}
