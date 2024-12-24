package com.omgservers.schema.entrypoint.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantSupportRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;
}
