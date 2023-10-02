package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
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
public class ChangeMessageReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long clientId;

    @NotNull
    Object data;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CHANGE_MESSAGE_RECEIVED;
    }

    @Override
    public Long getGroupId() {
        return clientId;
    }
}
