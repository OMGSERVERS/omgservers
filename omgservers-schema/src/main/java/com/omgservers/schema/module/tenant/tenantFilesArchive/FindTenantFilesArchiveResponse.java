package com.omgservers.schema.module.tenant.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantFilesArchiveResponse {

    TenantFilesArchiveModel tenantFilesArchive;
}
