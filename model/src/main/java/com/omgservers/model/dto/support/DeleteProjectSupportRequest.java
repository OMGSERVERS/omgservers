package com.omgservers.model.dto.support;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProjectSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;
}
