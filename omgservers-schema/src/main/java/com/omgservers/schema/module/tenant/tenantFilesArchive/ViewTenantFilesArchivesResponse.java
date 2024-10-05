package com.omgservers.schema.module.tenant.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantFilesArchivesResponse {

    List<TenantFilesArchiveProjectionModel> tenantFilesArchives;
}
