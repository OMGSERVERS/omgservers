package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDeveloperSupportRequest {

    @NotNull
    Long userId;
}
