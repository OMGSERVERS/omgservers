package com.omgservers.schema.event.body.internal;

import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.event.EventQualifierEnum;
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
public class RuntimeDeploymentRequestedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DEPLOYMENT_REQUESTED;
    }
}
