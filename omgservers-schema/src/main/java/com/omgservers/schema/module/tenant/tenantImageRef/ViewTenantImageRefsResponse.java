package com.omgservers.schema.module.tenant.tenantImageRef;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantImageRefsResponse {

    List<TenantImageRefModel> tenantImageRefs;
}
