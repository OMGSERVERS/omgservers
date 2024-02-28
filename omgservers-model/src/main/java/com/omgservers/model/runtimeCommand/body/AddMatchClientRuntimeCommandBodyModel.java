package com.omgservers.model.runtimeCommand.body;

import com.omgservers.model.runtimeCommand.RuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddMatchClientRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    String groupName;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_MATCH_CLIENT;
    }
}
