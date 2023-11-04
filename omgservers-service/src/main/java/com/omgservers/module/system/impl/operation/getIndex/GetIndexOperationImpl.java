package com.omgservers.module.system.impl.operation.getIndex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.index.IndexConfigModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexOperationImpl implements GetIndexOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<IndexModel> getIndex(final SqlConnection sqlConnection, final String name) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, name, version, config
                        from system.tab_index
                        where name = $1
                        limit 1
                        """,
                Collections.singletonList(name),
                "Index",
                this::createIndex);
    }

    IndexModel createIndex(Row row) {
        IndexModel index = new IndexModel();
        index.setId(row.getLong("id"));
        index.setCreated(row.getOffsetDateTime("created").toInstant());
        index.setModified(row.getOffsetDateTime("modified").toInstant());
        index.setName(row.getString("name"));
        index.setVersion(row.getLong("version"));
        try {
            index.setConfig(objectMapper.readValue(row.getString("config"), IndexConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("index config can't be parsed, index=" + index, e);
        }
        return index;
    }
}
