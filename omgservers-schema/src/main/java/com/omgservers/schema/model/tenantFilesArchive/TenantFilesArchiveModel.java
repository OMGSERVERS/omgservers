package com.omgservers.schema.model.tenantFilesArchive;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantFilesArchiveModel {

    @NotNull
    Long id;

    @NotBlank
    @ToString.Exclude
    String idempotencyKey;

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    String base64Archive;

    @NotNull
    Boolean deleted;

    public TenantFilesArchiveProjectionModel mapToProjection() {
        final var tenantFilesArchiveProjection = new TenantFilesArchiveProjectionModel();
        tenantFilesArchiveProjection.setId(id);
        tenantFilesArchiveProjection.setIdempotencyKey(idempotencyKey);
        tenantFilesArchiveProjection.setTenantId(tenantId);
        tenantFilesArchiveProjection.setVersionId(versionId);
        tenantFilesArchiveProjection.setCreated(created);
        tenantFilesArchiveProjection.setModified(modified);
        tenantFilesArchiveProjection.setDeleted(deleted);
        return tenantFilesArchiveProjection;
    }
}
