package com.omgservers.model.scriptRequest.arguments;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.scriptRequest.ScriptRequestArgumentsModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddClientRequestArgumentsModel extends ScriptRequestArgumentsModel {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    PlayerAttributesModel attributes;

    @NotNull
    Object object;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.ADD_CLIENT;
    }
}
