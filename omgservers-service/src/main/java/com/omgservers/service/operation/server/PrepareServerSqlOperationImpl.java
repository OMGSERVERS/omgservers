package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PrepareServerSqlOperationImpl implements PrepareServerSqlOperation {

    final long serverId;

    public PrepareServerSqlOperationImpl(final GetServiceConfigOperation getServiceConfigOperation) {
        serverId = getServiceConfigOperation.getServiceConfig().server().id();
    }

    @Override
    public String execute(final String sql) {
        final var serverSchema = String.format(DatabaseSchemaConfiguration.SERVER_SCHEMA_FORMAT, serverId);
        final var preparedSql = sql.replaceAll("\\$server", serverSchema);
        return preparedSql;
    }
}
