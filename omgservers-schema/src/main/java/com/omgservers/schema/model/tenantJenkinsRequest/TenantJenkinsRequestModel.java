package com.omgservers.schema.model.tenantJenkinsRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantJenkinsRequestModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    TenantJenkinsRequestQualifierEnum qualifier;

    @NotNull
    Integer buildNumber;

    @NotNull
    Boolean deleted;
}
