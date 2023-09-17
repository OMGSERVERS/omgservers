package com.omgservers.job.runtime.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.runtime.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.DeletePlayerRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.DeletePlayerScriptEventBodyModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeletePlayerRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.DELETE_PLAYER;
    }

    @Override
    public ScriptEventModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (DeletePlayerRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = DeletePlayerScriptEventBodyModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .playerId(runtimeCommandBody.getPlayerId())
                .clientId(runtimeCommandBody.getClientId())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
