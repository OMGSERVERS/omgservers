package com.omgservers.service.master.node.impl.mapper;

import com.omgservers.schema.model.node.NodeModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class NodeModelMapper {

    public NodeModel execute(final Row row) {
        final var node = new NodeModel();
        node.setId(row.getLong("id"));
        node.setModified(row.getOffsetDateTime("modified").toInstant());
        node.setDeleted(row.getBoolean("deleted"));
        return node;
    }
}
