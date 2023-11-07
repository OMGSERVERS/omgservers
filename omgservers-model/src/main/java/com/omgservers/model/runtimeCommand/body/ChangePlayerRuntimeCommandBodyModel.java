package com.omgservers.model.runtimeCommand.body;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ChangePlayerRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    PlayerAttributesModel attributes;

    @NotNull
    Object profile;

    @NotNull
    Object message;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.CHANGE_PLAYER;
    }
}
