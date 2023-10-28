package com.omgservers.model.event.body;

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
public class ClientDisconnectedEventBodyModel extends EventBodyModel {

    @NotNull
    Long connectionId;

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DISCONNECTED;
    }

    @Override
    public Long getGroupId() {
        return userId;
    }
}
