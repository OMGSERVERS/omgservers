package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createPlayerContextMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player.LuaPlayerContextFactory;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreatePlayerContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreatePlayerContextHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreatePlayerContextMethodImpl implements CreatePlayerContextMethod {

    final LuaPlayerContextFactory luaPlayerContextFactory;

    @Override
    public Uni<CreatePlayerContextHelpResponse> createPlayerContext(final CreatePlayerContextHelpRequest request) {
        CreatePlayerContextHelpRequest.validate(request);

        final var user = request.getUser();
        final var player = request.getPlayer();
        final var client = request.getClient();

        return Uni.createFrom().voidItem()
                .map(voidItem -> luaPlayerContextFactory.build(user, player, client))
                .map(CreatePlayerContextHelpResponse::new);
    }
}
