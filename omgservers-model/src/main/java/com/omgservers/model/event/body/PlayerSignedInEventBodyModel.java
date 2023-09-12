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
public class PlayerSignedInEventBodyModel extends EventBodyModel {

    Long tenantId;
    Long stageId;
    Long userId;
    Long playerId;
    Long clientId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.PLAYER_SIGNED_IN;
    }

    @Override
    public Long getGroupId() {
        return userId;
    }
}
