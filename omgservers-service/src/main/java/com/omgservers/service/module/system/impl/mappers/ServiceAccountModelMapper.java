package com.omgservers.service.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServiceAccountModelMapper {

    final ObjectMapper objectMapper;

    public ServiceAccountModel fromRow(Row row) {
        final var serviceAccount = new ServiceAccountModel();
        serviceAccount.setId(row.getLong("id"));
        serviceAccount.setCreated(row.getOffsetDateTime("created").toInstant());
        serviceAccount.setModified(row.getOffsetDateTime("modified").toInstant());
        serviceAccount.setUsername(row.getString("username"));
        serviceAccount.setPasswordHash(row.getString("password_hash"));
        serviceAccount.setDeleted(row.getBoolean("deleted"));
        return serviceAccount;
    }
}
