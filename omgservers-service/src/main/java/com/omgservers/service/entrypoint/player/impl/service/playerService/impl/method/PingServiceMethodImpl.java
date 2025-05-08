package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.PingServicePlayerRequest;
import com.omgservers.schema.entrypoint.player.PingServicePlayerResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class PingServiceMethodImpl implements PingServiceMethod {

    @Override
    public Uni<PingServicePlayerResponse> execute(final PingServicePlayerRequest request) {
        log.info("Requested, {}", request);

        return Uni.createFrom().item(new PingServicePlayerResponse("pong"));
    }
}
