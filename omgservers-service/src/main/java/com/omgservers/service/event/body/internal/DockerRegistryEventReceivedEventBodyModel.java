package com.omgservers.service.event.body.internal;

import com.omgservers.schema.entrypoint.registry.handleEvents.DockerRegistryEventDto;
import com.omgservers.service.event.EventBodyModel;
import com.omgservers.service.event.EventQualifierEnum;
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
