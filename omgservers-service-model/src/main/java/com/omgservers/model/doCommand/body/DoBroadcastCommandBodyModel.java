package com.omgservers.model.doCommand.body;

import com.omgservers.model.doCommand.DoCommandBodyModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
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
public class DoBroadcastCommandBodyModel extends DoCommandBodyModel {

    @NotNull
    @ToString.Exclude
    Object message;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_BROADCAST;
    }
}
