package com.omgservers.service.module.admin.impl.service.webService;

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

public interface WebService {

    Uni<PingServerAdminResponse> pingServer();

    Uni<GenerateIdAdminResponse> generateId();

    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request0);

    Uni<FindIndexAdminResponse> findIndex(FindIndexAdminRequest request);

    Uni<CreateIndexAdminResponse> createIndex(CreateIndexAdminRequest request);

    Uni<SyncIndexAdminResponse> syncIndex(SyncIndexAdminRequest request);

    Uni<FindServiceAccountAdminResponse> findServiceAccount(FindServiceAccountAdminRequest request);

    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);

    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request);

    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);
}
