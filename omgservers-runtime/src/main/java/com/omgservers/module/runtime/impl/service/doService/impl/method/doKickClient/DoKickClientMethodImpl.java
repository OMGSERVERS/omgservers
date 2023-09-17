package com.omgservers.module.runtime.impl.service.doService.impl.method.doKickClient;

import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoKickClientMethodImpl implements DoKickClientMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> Uni.createFrom().voidItem())
                .replaceWith(new DoKickClientResponse());
    }
}
