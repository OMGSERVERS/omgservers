package com.omgservers.module.runtime.impl.service.doService.impl.method.doMulticastMessage;

import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoMulticastMessageMethodImpl implements DoMulticastMessageMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> Uni.createFrom().voidItem())
                .replaceWith(new DoMulticastMessageResponse());
    }
}
