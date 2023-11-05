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
public class BroadcastCommandReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @NotNull
    Object message;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.BROADCAST_COMMAND_RECEIVED;
    }

    @Override
    public Long getGroupId() {
        return runtimeId;
    }
}
