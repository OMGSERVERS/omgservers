package com.omgservers.schema.event.body.internal;

import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InactiveRuntimeDetectedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INACTIVE_RUNTIME_DETECTED;
    }
}
