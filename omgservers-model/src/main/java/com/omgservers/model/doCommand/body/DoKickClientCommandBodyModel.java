package com.omgservers.model.doCommand.body;

import com.omgservers.model.doCommand.DoCommandBodyModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DoKickClientCommandBodyModel extends DoCommandBodyModel {

    @NotNull
    Long clientId;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_KICK_CLIENT;
    }
}
