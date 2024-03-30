package com.omgservers.service.factory;

import com.omgservers.model.server.ServerConfigModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.server.ServerQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ServerModel create(final Long poolId,
                              final ServerQualifierEnum qualifier,
                              final URI uri,
                              final Integer cpuCount,
                              final Integer memorySize,
                              final ServerConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, poolId, qualifier, uri, cpuCount, memorySize, config, idempotencyKey);
    }

    public ServerModel create(final Long poolId,
                              final ServerQualifierEnum qualifier,
                              final URI uri,
                              final Integer cpuCount,
                              final Integer memorySize,
                              final ServerConfigModel config,
                              final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, poolId, qualifier, uri, cpuCount, memorySize, config, idempotencyKey);
    }

    public ServerModel create(final Long id,
                              final Long poolId,
                              final ServerQualifierEnum qualifier,
                              final URI uri,
                              final Integer cpuCount,
                              final Integer memorySize,
                              final ServerConfigModel config,
                              final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var server = new ServerModel();
        server.setId(id);
        server.setIdempotencyKey(idempotencyKey);
        server.setCreated(now);
        server.setModified(now);
        server.setPoolId(poolId);
        server.setQualifier(qualifier);
        server.setUri(uri);
        server.setCpuCount(cpuCount);
        server.setCpuUsed(0);
        server.setMemorySize(memorySize);
        server.setMemoryUsed(0);
        server.setConfig(config);
        server.setDeleted(Boolean.FALSE);
        return server;
    }
}
