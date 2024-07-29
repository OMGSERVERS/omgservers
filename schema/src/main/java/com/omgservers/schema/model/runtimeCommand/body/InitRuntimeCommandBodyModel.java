package com.omgservers.schema.model.runtimeCommand.body;

import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeConfigModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InitRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    @NotNull
    RuntimeConfigModel config;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.INIT_RUNTIME;
    }
}
