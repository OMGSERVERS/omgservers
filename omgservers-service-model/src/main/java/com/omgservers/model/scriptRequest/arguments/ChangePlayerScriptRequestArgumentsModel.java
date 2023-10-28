package com.omgservers.model.scriptRequest.arguments;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.scriptRequest.ScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChangePlayerScriptRequestArgumentsModel extends ScriptRequestArgumentsModel {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Long clientId;

    @NotNull
    PlayerAttributesModel attributes;

    @NotNull
    Object object;

    @NotNull
    Object message;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.CHANGE_PLAYER;
    }
}
