package com.omgservers.model.runtimeCommand.body;

import com.omgservers.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.version.VersionModeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StartRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    VersionModeModel mode;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.START;
    }
}
