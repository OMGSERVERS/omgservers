package com.omgservers.schema.model.runtimeCommand.body;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyDto;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AssignClientRuntimeCommandBodyDto extends RuntimeCommandBodyDto {

    @NotNull
    Long clientId;

    String groupName;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ASSIGN_CLIENT;
    }
}
