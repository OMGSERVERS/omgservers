package com.omgservers.module.internal.impl.operation.selectServiceAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServiceAccountOperationImpl implements SelectServiceAccountOperation {

    static private final String SQL = """
            select id, created, modified, username, password_hash
            from internal.tab_service_account where username = $1 limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ServiceAccountModel> selectServiceAccount(final SqlConnection sqlConnection, final String username) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (username == null) {
            throw new ServerSideBadRequestException("username is null");
        }

        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.of(username))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.debug("Service account was found, username={}", username);
                        return createServiceAccount(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException("service account was not found, username=" + username);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    ServiceAccountModel createServiceAccount(Row row) {
        ServiceAccountModel serviceAccount = new ServiceAccountModel();
        serviceAccount.setId(row.getLong("id"));
        serviceAccount.setCreated(row.getOffsetDateTime("created").toInstant());
        serviceAccount.setModified(row.getOffsetDateTime("modified").toInstant());
        serviceAccount.setUsername(row.getString("username"));
        serviceAccount.setPasswordHash(row.getString("password_hash"));
        return serviceAccount;
    }
}
