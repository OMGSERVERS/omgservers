package com.omgservers.service.entrypoint.admin.impl.service.adminService.impl.method;

import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.service.operation.server.CalculateShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CalculateShardMethodImpl implements CalculateShardMethod {

    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<CalculateShardAdminResponse> execute(final CalculateShardAdminRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getShardKey();

        return calculateShardOperation.calculateShard(shardKey)
                .map(shardModel -> new CalculateShardAdminResponse(shardModel.shard(),
                        shardModel.serverUri()));
    }
}
