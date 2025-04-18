package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PrepareSlotSqlOperationImpl implements PrepareSlotSqlOperation {

    @Override
    public String execute(final String sql,
                          final int slot) {
        final var slotSchema = String.format(DatabaseSchemaConfiguration.SLOT_SCHEMA_FORMAT, slot);
        final var preparedSql = sql.replaceAll("\\$slot", slotSchema);
        return preparedSql;
    }
}
