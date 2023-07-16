package com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl.method.fireEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.ProducerHelpService;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FireEventMethodImpl implements FireEventMethod {

    final ProducerHelpService producerHelpService;

    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> fireEvent(final FireEventInternalRequest request) {
        FireEventInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var event = request.getEvent();
                    final var producerEventHelpRequest = new ProducerEventHelpRequest(event);
                    return producerHelpService.produceEvent(producerEventHelpRequest);
                });
    }
}
