package com.omgservers.model.event.body;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StopCommandReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @NotEmpty
    String reason;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STOP_COMMAND_RECEIVED;
    }

    @Override
    public Long getGroupId() {
        return runtimeId;
    }
}
