package com.omgservers.service.module.admin.impl.service.adminService;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.BcryptHashAdminResponse;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.admin.DeleteTenantAdminResponse;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminResponse;
import com.omgservers.model.dto.admin.FindServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.FindServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.GenerateIdAdminResponse;
import com.omgservers.model.dto.admin.PingServerAdminResponse;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface AdminService {
    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<BcryptHashAdminResponse> bcryptHash(@Valid BcryptHashAdminRequest request);

    Uni<FindIndexAdminResponse> findIndex(@Valid FindIndexAdminRequest request);

    Uni<CreateIndexAdminResponse> createIndex(@Valid CreateIndexAdminRequest request);

    Uni<SyncIndexAdminResponse> syncIndex(@Valid SyncIndexAdminRequest request);

    Uni<FindServiceAccountAdminResponse> findServiceAccount(@Valid FindServiceAccountAdminRequest request);

    Uni<CreateServiceAccountAdminResponse> createServiceAccount(@Valid CreateServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(@Valid CreateTenantAdminRequest request);

    Uni<DeleteTenantAdminResponse> deleteTenant(@Valid DeleteTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(@Valid CreateDeveloperAdminRequest request);
}
