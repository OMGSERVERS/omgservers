package com.omgservers.service.event.body.internal;

import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LobbyAssignmentRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    Long tenantId;

    @NotNull
    Long deploymentId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_ASSIGNMENT_REQUESTED;
    }
}
