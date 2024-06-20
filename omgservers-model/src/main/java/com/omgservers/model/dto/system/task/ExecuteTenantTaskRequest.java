package com.omgservers.model.dto.system.task;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteTenantTaskRequest {

    @NotNull
    Long tenantId;

}
