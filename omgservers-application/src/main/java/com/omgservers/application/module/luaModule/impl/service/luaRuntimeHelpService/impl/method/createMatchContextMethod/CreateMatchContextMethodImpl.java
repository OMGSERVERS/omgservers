package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createMatchContextMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.match.LuaMatchContextFactory;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateMatchContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateMatchContextHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateMatchContextMethodImpl implements CreateMatchContextMethod {

    final LuaMatchContextFactory luaMatchContextFactory;

    @Override
    public Uni<CreateMatchContextHelpResponse> createMatchContext(final CreateMatchContextHelpRequest request) {
        CreateMatchContextHelpRequest.validate(request);

        return Uni.createFrom().voidItem()
                .map(voidItem -> {
                    final var matchmaker = request.getMatchmaker();
                    final var match = request.getMatch();
                    return luaMatchContextFactory.build(matchmaker, match);
                })
                .map(CreateMatchContextHelpResponse::new);
    }
}
