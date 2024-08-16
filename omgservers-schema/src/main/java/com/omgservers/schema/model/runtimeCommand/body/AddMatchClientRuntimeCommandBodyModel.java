package com.omgservers.schema.model.runtimeCommand.body;

import com.omgservers.schema.model.player.PlayerAttributesModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddMatchClientRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    @NotNull
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    String groupName;

    @NotNull
    PlayerAttributesModel attributes;

    @NotNull
    Object profile;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_MATCH_CLIENT;
    }
}
