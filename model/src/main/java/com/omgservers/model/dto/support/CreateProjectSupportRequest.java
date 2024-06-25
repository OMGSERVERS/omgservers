package com.omgservers.model.dto.support;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProjectSupportRequest {

    @NotNull
    Long tenantId;
}
