package com.omgservers.service.factory;

import com.omgservers.model.container.ContainerConfigModel;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ContainerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ContainerModel create(final Long entityId,
                                 final ContainerQualifierEnum qualifier,
                                 final String image,
                                 final ContainerConfigModel config) {
        final var id = generateIdOperation.generateId();
        return create(id, entityId, qualifier, image, config);
    }

    public ContainerModel create(final Long id,
                                 final Long entityId,
                                 final ContainerQualifierEnum qualifier,
                                 final String image,
                                 final ContainerConfigModel config) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var container = new ContainerModel();
        container.setId(id);
        container.setCreated(now);
        container.setModified(now);
        container.setEntityId(entityId);
        container.setQualifier(qualifier);
        container.setImage(image);
        container.setConfig(config);
        container.setDeleted(false);
        return container;
    }
}
