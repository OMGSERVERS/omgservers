package com.omgservers.service.operation.server;

import com.omgservers.service.configuration.DatabaseSchemaConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class PrepareSqlOperationImpl implements PrepareSqlOperation {

    final String masterSchema;
    final String shardSchema;

    public PrepareSqlOperationImpl(final GetServiceConfigOperation getServiceConfigOperation) {
        final var shardId = getServiceConfigOperation.getServiceConfig().shard().id();
        masterSchema = String.format(DatabaseSchemaConfiguration.MASTER_SCHEMA_FORMAT, shardId);
        shardSchema = String.format(DatabaseSchemaConfiguration.SHARD_SCHEMA_FORMAT, shardId);
    }

    @Override
    public String execute(final String sql) {
        final var preparedSql = sql.replaceAll("\\$shard", shardSchema)
                .replaceAll("\\$master", masterSchema);
        return preparedSql;
    }
}
