package com.omgservers.module.matchmaker.impl.operation.upsertMatchmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
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

import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertMatchmakerOperationImpl implements UpsertMatchmakerOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker(id, created, modified, tenant_id, stage_id)
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
    public Uni<Boolean> upsertMatchmaker(final ChangeContext changeContext,
                                         final SqlConnection sqlConnection,
                                         final int shard,
                                         final MatchmakerModel matchmaker) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (matchmaker == null) {
            throw new ServerSideBadRequestException("matchmaker is null");
        }

        return upsertObject(sqlConnection, shard, matchmaker)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, matchmaker))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, matchmaker))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Matchmaker was created, shard={}, matchmaker={}", shard, matchmaker);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection,
                              final int shard,
                              final MatchmakerModel matchmaker) {
        var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(new ArrayList<>() {{
                    add(matchmaker.getId());
                    add(matchmaker.getCreated().atOffset(ZoneOffset.UTC));
                    add(matchmaker.getModified().atOffset(ZoneOffset.UTC));
                    add(matchmaker.getTenantId());
                    add(matchmaker.getStageId());
                }}))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext changeContext,
                             final SqlConnection sqlConnection,
                             final MatchmakerModel matchmaker) {
        if (objectWasInserted) {
            final var body = new MatchmakerCreatedEventBodyModel(matchmaker.getId());
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
                           final MatchmakerModel matchmaker) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Matchmaker was inserted, matchmaker=" + matchmaker);
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
