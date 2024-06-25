package com.omgservers.model.dto.support;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteDeveloperSupportRequest {

    @NotNull
    Long userId;
}
