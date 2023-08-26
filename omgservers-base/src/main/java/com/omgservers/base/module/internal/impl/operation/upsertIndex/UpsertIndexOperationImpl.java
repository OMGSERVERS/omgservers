package com.omgservers.base.module.internal.impl.operation.upsertIndex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertIndexOperationImpl implements UpsertIndexOperation {

    static private final String sql = """
            insert into internal.tab_index(id, created, modified, name, version, config)
            values($1, $2, $3, $4, $5, $6)
            on conflict (id) do
            update set modified = $3, version = $5, config = $6
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> upsertIndex(final SqlConnection sqlConnection, final IndexModel index) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (index == null) {
            throw new ServerSideBadRequestException("index is null");
        }

        return upsertQuery(sqlConnection, index)
                .invoke(voidItem -> log.info("Index was synchronized, name={}, version={}",
                        index.getName(), index.getVersion()));
    }

    Uni<Void> upsertQuery(SqlConnection sqlConnection, IndexModel index) {
        try {
            String configString = objectMapper.writeValueAsString(index.getConfig());
            return sqlConnection.preparedQuery(sql)
                    .execute(Tuple.of(index.getId(),
                            index.getCreated().atOffset(ZoneOffset.UTC),
                            index.getModified().atOffset(ZoneOffset.UTC),
                            index.getName(),
                            index.getVersion(),
                            configString))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
