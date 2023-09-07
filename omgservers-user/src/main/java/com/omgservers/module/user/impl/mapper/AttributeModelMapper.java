package com.omgservers.module.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.attribute.AttributeModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AttributeModelMapper {

    final ObjectMapper objectMapper;

    public AttributeModel fromRow(Row row) {
        AttributeModel attribute = new AttributeModel();
        attribute.setId(row.getLong("id"));
        attribute.setUserId(row.getLong("user_id"));
        attribute.setPlayerId(row.getLong("player_id"));
        attribute.setCreated(row.getOffsetDateTime("created").toInstant());
        attribute.setModified(row.getOffsetDateTime("modified").toInstant());
        attribute.setName(row.getString("attribute_name"));
        attribute.setValue(row.getString("attribute_value"));
        return attribute;
    }
}
