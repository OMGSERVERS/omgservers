package com.omgservers.base.impl.operation.getIndexOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexOperationImpl implements GetIndexOperation {

    static private final String sql = """
            select id, created, modified, name, version, config
            from internal.tab_index where name = $1 limit 1
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<IndexModel> getIndex(final SqlConnection sqlConnection, final String name) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(name))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            log.debug("Index was found, name={}", name);
                            return createIndex(iterator.next());
                        } catch (IOException e) {
                            throw new ServerSideInternalException("index can't be parsed, name=" + name, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("index was not found, name=" + name);
                    }
                });
    }

    IndexModel createIndex(Row row) throws IOException {
        IndexModel index = new IndexModel();
        index.setId(row.getLong("id"));
        index.setCreated(row.getOffsetDateTime("created").toInstant());
        index.setModified(row.getOffsetDateTime("modified").toInstant());
        index.setName(row.getString("name"));
        index.setVersion(row.getLong("version"));
        index.setConfig(objectMapper.readValue(row.getString("config"), IndexConfigModel.class));
        return index;
    }
}
