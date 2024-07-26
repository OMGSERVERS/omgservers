package com.omgservers.model.event.body.internal;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.registry.DockerRegistryEventDto;
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
public class DockerRegistryEventReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    DockerRegistryEventDto event;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DOCKER_REGISTRY_EVENT_RECEIVED;
    }
}
