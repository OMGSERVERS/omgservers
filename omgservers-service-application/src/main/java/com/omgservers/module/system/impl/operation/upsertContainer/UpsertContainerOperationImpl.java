package com.omgservers.module.system.impl.operation.upsertContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.event.body.ContainerCreatedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertContainerOperationImpl implements UpsertContainerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertContainer(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final ContainerModel container) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into system.tab_container(
                            id, created, modified, entity_id, qualifier, image, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        container.getId(),
                        container.getCreated().atOffset(ZoneOffset.UTC),
                        container.getModified().atOffset(ZoneOffset.UTC),
                        container.getEntityId(),
                        container.getQualifier(),
                        container.getImage(),
                        getConfigString(container),
                        container.getDeleted()
                ),
                () -> new ContainerCreatedEventBodyModel(container.getId()),
                () -> logModelFactory.create("Container was created, container=" + container)
        );
    }

    String getConfigString(ContainerModel container) {
        try {
            return objectMapper.writeValueAsString(container.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
