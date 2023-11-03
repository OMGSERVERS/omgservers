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
public class DoStopCommandBodyModel extends DoCommandBodyModel {

    @NotNull
    String reason;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_STOP;
    }
}
