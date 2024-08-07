package com.omgservers.schema.model.outgoingCommand.body;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.player.PlayerAttributesModel;
import com.omgservers.schema.model.player.PlayerAttributesModel;
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
public class SetAttributesOutgoingCommandBodyModel extends OutgoingCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    PlayerAttributesModel attributes;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.SET_ATTRIBUTES;
    }
}
