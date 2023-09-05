package com.omgservers.module.tenant.impl.operation.upsertProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertProjectOperationImpl implements UpsertProjectOperation {

    static private final String sql = """
            insert into $schema.tab_tenant_project(id, tenant_id, created, modified, config)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertProject(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final ProjectModel project) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }

        return upsertObject(sqlConnection, shard, project)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, project))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, project))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Project was inserted, shard={}, project={}", shard, project);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, ProjectModel project) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(project.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            project.getId(),
                            project.getTenantId(),
                            project.getCreated().atOffset(ZoneOffset.UTC),
                            project.getModified().atOffset(ZoneOffset.UTC),
                            configString
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final ProjectModel project) {
        if (objectWasInserted) {
            final var body = new ProjectCreatedEventBodyModel(project.getTenantId(), project.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(sqlConnection, event)
                    .invoke(eventWasInserted -> {
                        if (eventWasInserted) {
                            changeContext.add(event);
                        }
                    });
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final ProjectModel project) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Project was created, project=" + project);
            return upsertLogOperation.upsertLog(sqlConnection, changeLog)
                    .invoke(logWasInserted -> {
                        if (logWasInserted) {
                            changeContext.add(changeLog);
                        }
                    });
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
