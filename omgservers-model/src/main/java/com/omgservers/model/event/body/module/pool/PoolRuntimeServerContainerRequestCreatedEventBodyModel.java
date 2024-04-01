package com.omgservers.model.event.body.module.pool;

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
public class PoolRuntimeServerContainerRequestCreatedEventBodyModel extends EventBodyModel {

    @NotNull
    Long poolId;

    @NotNull
    Long id;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_RUNTIME_SERVER_CONTAINER_REQUEST_CREATED;
    }
}
