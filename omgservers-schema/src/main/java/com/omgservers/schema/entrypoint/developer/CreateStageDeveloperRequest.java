package com.omgservers.schema.entrypoint.developer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStageDeveloperRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotBlank
    @Size(max = 64)
    String project;
}
