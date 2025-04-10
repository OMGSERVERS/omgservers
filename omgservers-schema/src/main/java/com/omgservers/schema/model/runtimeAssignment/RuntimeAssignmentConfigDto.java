package com.omgservers.schema.model.runtimeAssignment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeAssignmentConfigDto {

    static public RuntimeAssignmentConfigDto create() {
        final var runtimeAssignmentConfig = new RuntimeAssignmentConfigDto();
        runtimeAssignmentConfig.setVersion(RuntimeAssignmentConfigVersionEnum.V1);
        return runtimeAssignmentConfig;
    }

    @NotNull
    RuntimeAssignmentConfigVersionEnum version;

    String groupName;
}
