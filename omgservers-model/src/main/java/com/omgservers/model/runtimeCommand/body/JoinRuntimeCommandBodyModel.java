package com.omgservers.model.runtimeCommand.body;

import com.omgservers.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JoinRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    Long userId;
    Long clientId;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.JOIN;
    }
}
