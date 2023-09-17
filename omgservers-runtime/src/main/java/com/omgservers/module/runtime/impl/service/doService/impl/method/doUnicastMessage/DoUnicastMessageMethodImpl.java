package com.omgservers.module.runtime.impl.service.doService.impl.method.doUnicastMessage;

import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoUnicastMessageMethodImpl implements DoUnicastMessageMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DoUnicastMessageResponse> doUnicastMessage(final DoUnicastMessageRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> Uni.createFrom().voidItem())
                .replaceWith(new DoUnicastMessageResponse());
    }
}
