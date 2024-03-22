package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

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
import com.omgservers.model.dto.admin.SyncServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.SyncServiceAccountAdminResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Admin Entrypoint API")
@Path("/omgservers/v1/entrypoint/admin/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.ADMIN_SECURITY_SCHEMA)
public interface AdminApi {

    @PUT
    @Path("/ping-server")
    Uni<PingServerAdminResponse> pingServer();

    @PUT
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> generateId();

    @PUT
    @Path("/bcrypt-hash")
    Uni<BcryptHashAdminResponse> bcryptHash(BcryptHashAdminRequest request);

    @PUT
    @Path("/find-index")
    Uni<FindIndexAdminResponse> findIndex(FindIndexAdminRequest request);

    @PUT
    @Path("/create-index")
    Uni<CreateIndexAdminResponse> createIndex(CreateIndexAdminRequest request);

    @PUT
    @Path("/sync-index")
    Uni<SyncIndexAdminResponse> syncIndex(SyncIndexAdminRequest request);

    @PUT
    @Path("/find-service-account")
    Uni<FindServiceAccountAdminResponse> findServiceAccount(FindServiceAccountAdminRequest request);

    @PUT
    @Path("/create-service-account")
    Uni<CreateServiceAccountAdminResponse> createServiceAccount(CreateServiceAccountAdminRequest request);

    @PUT
    @Path("/sync-service-account")
    Uni<SyncServiceAccountAdminResponse> syncServiceAccount(SyncServiceAccountAdminRequest request);

    @PUT
    @Path("/create-tenant")
    Uni<CreateTenantAdminResponse> createTenant(CreateTenantAdminRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<DeleteTenantAdminResponse> deleteTenant(DeleteTenantAdminRequest request);

    @PUT
    @Path("/create-developer")
    Uni<CreateDeveloperAdminResponse> createDeveloper(CreateDeveloperAdminRequest request);

}
