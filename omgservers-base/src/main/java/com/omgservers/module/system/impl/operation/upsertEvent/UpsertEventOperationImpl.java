package com.omgservers.module.system.impl.operation.upsertEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.EventModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
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
class UpsertEventOperationImpl implements UpsertEventOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertEvent(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final EventModel event) {
        return executeChangeObjectOperation.executeChangeObject(
                        changeContext, sqlConnection, 0,
                        """
                                insert into system.tab_event(id, created, modified, group_id, qualifier, relayed, body, status)
                                values($1, $2, $3, $4, $5, $6, $7, $8)
                                on conflict (id) do
                                nothing
                                """,
                        Arrays.asList(
                                event.getId(),
                                event.getCreated().atOffset(ZoneOffset.UTC),
                                event.getModified().atOffset(ZoneOffset.UTC),
                                event.getGroupId(),
                                event.getQualifier(),
                                event.getRelayed(),
                                getBodyString(event),
                                event.getStatus()),
                        () -> null,
                        () -> logModelFactory.create("Event was inserted, event=" + event)
                )
                .invoke(eventWasInserted -> {
                    if (eventWasInserted) {
                        changeContext.add(event);
                    }
                });
    }

    String getBodyString(EventModel event) {
        try {
            return objectMapper.writeValueAsString(event.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
