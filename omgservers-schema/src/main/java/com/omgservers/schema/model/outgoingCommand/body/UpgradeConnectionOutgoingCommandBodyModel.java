package com.omgservers.schema.model.outgoingCommand.body;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpgradeConnectionOutgoingCommandBodyModel extends OutgoingCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    UpgradeConnectionQualifierEnum protocol;

    @Override
    public OutgoingCommandQualifierEnum getQualifier() {
        return OutgoingCommandQualifierEnum.UPGRADE_CONNECTION;
    }
}
