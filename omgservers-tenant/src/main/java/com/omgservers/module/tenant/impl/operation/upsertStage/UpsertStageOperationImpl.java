package com.omgservers.module.tenant.impl.operation.upsertStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
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
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertStageOperationImpl implements UpsertStageOperation {

    static private final String sql = """
            insert into $schema.tab_tenant_stage(id, project_id, created, modified, secret, matchmaker_id, config)
            values($1, $2, $3, $4, $5, $6, $7)
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
    public Uni<Boolean> upsertStage(final ChangeContext changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final StageModel stage) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }

        return upsertObject(sqlConnection, shard, stage)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, tenantId, stage))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, tenantId, stage))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Stage was inserted, shard={}, tenantId={}, stage={}", shard, tenantId, stage);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, StageModel stage) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(stage.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(stage.getId());
                        add(stage.getProjectId());
                        add(stage.getCreated().atOffset(ZoneOffset.UTC));
                        add(stage.getModified().atOffset(ZoneOffset.UTC));
                        add(stage.getSecret());
                        add(stage.getMatchmakerId());
                        add(configString);
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext changeContext,
                             final SqlConnection sqlConnection,
                             final Long tenantId,
                             final StageModel stage) {
        if (objectWasInserted) {
            final var body = new StageCreatedEventBodyModel(tenantId, stage.getId());
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
                           final ChangeContext changeContext,
                           final SqlConnection sqlConnection,
                           final Long tenantId,
                           final StageModel stage) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Stage was created, " +
                    "tenantId=%d, stage=%s", tenantId, stage));
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
