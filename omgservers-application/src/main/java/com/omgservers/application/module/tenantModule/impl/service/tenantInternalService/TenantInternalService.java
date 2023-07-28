package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService;

import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.*;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.GetTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantInternalService {

    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);

    Uni<Void> deleteTenant(DeleteTenantInternalRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request);
}
