package com.omgservers.schema.module.tenant.tenantMatchmakerRef;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantMatchmakerRefsResponse {

    List<TenantMatchmakerRefModel> tenantMatchmakerRefs;
}
