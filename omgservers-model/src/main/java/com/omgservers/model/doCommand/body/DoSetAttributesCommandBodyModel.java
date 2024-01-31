package com.omgservers.model.doCommand.body;

import com.omgservers.model.doCommand.DoCommandBodyModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.player.PlayerAttributesModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DoSetAttributesCommandBodyModel extends DoCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    PlayerAttributesModel attributes;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_SET_ATTRIBUTES;
    }
}
