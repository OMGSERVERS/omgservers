package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherResponse;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatcher;
import com.omgservers.dispatcher.server.dispatcher.impl.component.Dispatchers;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDispatcherMethodImpl implements CreateDispatcherMethod {

    final Dispatchers dispatchers;

    @Override
    public Uni<CreateDispatcherResponse> execute(final CreateDispatcherRequest request) {
        log.debug("{}", request);

        final var runtimeConnection = request.getRuntimeConnection();
        final var runtimeId = runtimeConnection.getRuntimeId();

        final var dispatcher = new Dispatcher(runtimeConnection);

        if (Objects.isNull(dispatchers.putIfAbsent(dispatcher))) {
            log.info("Dispatcher \"{}\" created", runtimeId);
            return Uni.createFrom().item(new CreateDispatcherResponse(Boolean.TRUE));
        } else {
            log.warn("Dispatcher \"{}\" already created", runtimeId);
            return Uni.createFrom().item(new CreateDispatcherResponse(Boolean.FALSE));
        }
    }
}
