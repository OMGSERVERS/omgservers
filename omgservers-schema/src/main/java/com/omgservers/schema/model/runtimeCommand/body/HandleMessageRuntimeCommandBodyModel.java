package com.omgservers.schema.model.runtimeCommand.body;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandBodyModel;
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
public class HandleMessageRuntimeCommandBodyModel extends RuntimeCommandBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    Object message;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.HANDLE_MESSAGE;
    }
}
