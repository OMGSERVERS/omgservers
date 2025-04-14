package com.omgservers.schema.shard.tenant.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantVersionsResponse {

    List<TenantVersionProjectionModel> tenantVersionProjections;
}
