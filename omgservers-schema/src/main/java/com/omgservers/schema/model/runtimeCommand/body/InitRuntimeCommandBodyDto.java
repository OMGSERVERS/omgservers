package com.omgservers.schema.model.runtimeCommand.body;

import com.omgservers.schema.model.runtime.RuntimeConfigDto;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
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
public class InitRuntimeCommandBodyDto extends RuntimeCommandBodyDto {

    @NotNull
    RuntimeConfigDto runtimeConfig;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.INIT_RUNTIME;
    }
}
