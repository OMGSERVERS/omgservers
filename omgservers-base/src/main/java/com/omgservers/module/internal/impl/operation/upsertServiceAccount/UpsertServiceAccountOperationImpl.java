package com.omgservers.module.internal.impl.operation.upsertServiceAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertServiceAccountOperationImpl implements UpsertServiceAccountOperation {

    private static final String SQL = """
            insert into internal.tab_service_account(id, created, modified, username, password_hash)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertServiceAccount(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final ServiceAccountModel serviceAccount) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (serviceAccount == null) {
            throw new ServerSideBadRequestException("serviceAccount is null");
        }

        return upsertObject(sqlConnection, serviceAccount)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, serviceAccount))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, serviceAccount))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Service account was inserted, serviceAccount={}", serviceAccount);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, ServiceAccountModel serviceAccount) {
        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.of(serviceAccount.getId(),
                        serviceAccount.getCreated().atOffset(ZoneOffset.UTC),
                        serviceAccount.getModified().atOffset(ZoneOffset.UTC),
                        serviceAccount.getUsername(),
                        serviceAccount.getPasswordHash()))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final ServiceAccountModel serviceAccount) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final ServiceAccountModel serviceAccount) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Service account was inserted, " +
                    "serviceAccount=" + serviceAccount);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
