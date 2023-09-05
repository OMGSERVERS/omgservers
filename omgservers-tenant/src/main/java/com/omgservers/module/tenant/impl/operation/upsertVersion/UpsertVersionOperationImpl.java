package com.omgservers.module.tenant.impl.operation.upsertVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModel;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertVersionOperationImpl implements UpsertVersionOperation {

    static private final String SQL = """
            insert into $schema.tab_tenant_version(id, stage_id, created, modified, config, source_code, bytecode, errors)
            values($1, $2, $3, $4, $5, $6, $7, $8)
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
    public Uni<Boolean> upsertVersion(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long tenantId,
                                      final VersionModel version) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tenantId == null) {
            throw new ServerSideBadRequestException("tenantId is null");
        }
        if (version == null) {
            throw new ServerSideBadRequestException("version is null");
        }

        return upsertObject(sqlConnection, shard, version)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, tenantId, version))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, tenantId, version))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Version was inserted, tenantId={}, version={}", tenantId, version);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, VersionModel version) {
        try {
            final var preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);
            final var config = objectMapper.writeValueAsString(version.getConfig());
            final var sourceCode = objectMapper.writeValueAsString(version.getSourceCode());
            final var bytecode = objectMapper.writeValueAsString(version.getBytecode());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            version.getId(),
                            version.getStageId(),
                            version.getCreated().atOffset(ZoneOffset.UTC),
                            version.getModified().atOffset(ZoneOffset.UTC),
                            config,
                            sourceCode,
                            bytecode,
                            version.getErrors()
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long tenantId,
                             final VersionModel version) {
        if (objectWasInserted) {
            final var body = new VersionCreatedEventBodyModel(tenantId, version.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long tenantId,
                           final VersionModel version) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Stage was created, " +
                    "tenantId=%d, version=%s", tenantId, version));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
