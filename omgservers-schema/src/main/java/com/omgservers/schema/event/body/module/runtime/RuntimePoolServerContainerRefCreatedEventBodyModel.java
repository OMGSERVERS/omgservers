package com.omgservers.schema.event.body.module.runtime;

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
public class RuntimePoolServerContainerRefCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long runtimeId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_POOL_SERVER_CONTAINER_REF_CREATED;
    }
}
