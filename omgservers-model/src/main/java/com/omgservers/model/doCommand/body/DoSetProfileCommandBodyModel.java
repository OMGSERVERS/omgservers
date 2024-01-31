package com.omgservers.model.doCommand.body;

import com.omgservers.model.doCommand.DoCommandBodyModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DoSetProfileCommandBodyModel extends DoCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    @ToString.Exclude
    Object profile;

    @Override
    public DoCommandQualifierEnum getQualifier() {
        return DoCommandQualifierEnum.DO_SET_PROFILE;
    }
}
