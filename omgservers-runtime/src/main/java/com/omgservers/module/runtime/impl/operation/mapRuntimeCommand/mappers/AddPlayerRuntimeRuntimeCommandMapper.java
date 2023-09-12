package com.omgservers.module.runtime.impl.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddPlayerRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.AddPlayerEventBodyModel;
import com.omgservers.module.runtime.impl.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddPlayerRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_PLAYER;
    }

    @Override
    public ScriptEventModel map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (AddPlayerRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = AddPlayerEventBodyModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .playerId(runtimeCommandBody.getPlayerId())
                .clientId(runtimeCommandBody.getClientId())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}