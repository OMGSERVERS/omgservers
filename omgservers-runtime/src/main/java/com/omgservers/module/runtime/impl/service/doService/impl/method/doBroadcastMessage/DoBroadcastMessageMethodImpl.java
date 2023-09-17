package com.omgservers.module.runtime.impl.service.doService.impl.method.doBroadcastMessage;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoBroadcastMessageMethodImpl implements DoBroadcastMessageMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> Uni.createFrom().voidItem())
                .replaceWith(new DoBroadcastMessageResponse());
    }
}
