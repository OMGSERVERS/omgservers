package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageDeveloperRequest {

    @NotBlank
    @Size(max = 64)
    String tenant;

    @NotBlank
    @Size(max = 64)
    String project;

    @NotBlank
    @Size(max = 64)
    String version;

    @NotNull
    TenantImageQualifierEnum qualifier;

    @NotBlank
    @Size(max = 1024)
    String image;

}
