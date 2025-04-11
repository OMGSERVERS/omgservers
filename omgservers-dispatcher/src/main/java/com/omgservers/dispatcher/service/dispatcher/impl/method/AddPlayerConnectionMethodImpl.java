package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.dispatcher.impl.component.Dispatchers;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AddPlayerConnectionMethodImpl implements AddPlayerConnectionMethod {

    final Dispatchers dispatchers;

    @Override
    public Uni<AddPlayerConnectionResponse> execute(final AddPlayerConnectionRequest request) {
        log.trace("{}", request);

        final var playerConnection = request.getPlayerConnection();
        final var runtimeId = playerConnection.getRuntimeId();
        final var userRole = playerConnection.getUserRole();

        final var dispatcher = dispatchers.getDispatcher(runtimeId);
        if (Objects.isNull(dispatcher)) {
            log.warn("Dispatcher not found, {}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }

        if (!userRole.equals(UserRoleEnum.PLAYER)) {
            log.warn("Wrong user role, {}", playerConnection);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }

        final var subject = playerConnection.getSubject();
        if (Objects.isNull(dispatcher.putIfAbsent(playerConnection))) {
            log.info("Player \"{}\" added to runtime \"{}\"", subject, runtimeId);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.TRUE));
        } else {
            log.warn("Player \"{}\" already in runtime \"{}\"", subject, runtimeId);
            return Uni.createFrom().item(new AddPlayerConnectionResponse(Boolean.FALSE));
        }
    }
}
