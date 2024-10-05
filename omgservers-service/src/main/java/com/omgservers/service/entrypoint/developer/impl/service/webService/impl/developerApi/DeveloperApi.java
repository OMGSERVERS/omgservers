package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Tag(name = "Developer Entrypoint API")
@Path("/omgservers/v1/entrypoint/developer/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.DEVELOPER_SECURITY_SCHEMA)
public interface DeveloperApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> createToken(@NotNull CreateTokenDeveloperRequest request);

    /*
    Tenant
     */

    @PUT
    @Path("/get-tenant-dashboard")
    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(@NotNull GetTenantDashboardDeveloperRequest request);

    /*
    Tenant project
     */

    @PUT
    @Path("/create-tenant-project")
    Uni<CreateTenantProjectDeveloperResponse> createTenantProject(@NotNull CreateTenantProjectDeveloperRequest request);

    @PUT
    @Path("/get-tenant-project-dashboard")
    Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            @NotNull GetTenantProjectDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-tenant-project")
    Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(@NotNull DeleteTenantProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    @PUT
    @Path("/create-tenant-stage")
    Uni<CreateTenantStageDeveloperResponse> createTenantStage(@NotNull CreateTenantStageDeveloperRequest request);

    @PUT
    @Path("/get-tenant-stage-dashboard")
    Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            @NotNull GetTenantStageDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-tenant-stage")
    Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(@NotNull DeleteTenantStageDeveloperRequest request);

    /*
    Tenant version
     */

    @PUT
    @Path("/create-tenant-version")
    Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(@NotNull CreateTenantVersionDeveloperRequest request);

    @PUT
    @Path("/upload-files-archive")
    Uni<UploadFilesArchiveDeveloperResponse> uploadFilesArchive(@RestForm("tenantId") Long tenantId,
                                                                @RestForm("tenantVersionId") Long tenantVersionId,
                                                                @RestForm(FileUpload.ALL) List<FileUpload> files);

    @PUT
    @Path("/get-tenant-version-dashboard")
    Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            @NotNull GetTenantVersionDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-tenant-version")
    Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(@NotNull DeleteTenantVersionDeveloperRequest request);

    /*
    Tenant deployment
     */

    @PUT
    @Path("/deploy-tenant-version")
    Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(@NotNull DeployTenantVersionDeveloperRequest request);

    @PUT
    @Path("/get-tenant-deployment-dashboard")
    Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            @NotNull GetTenantDeploymentDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-tenant-deployment")
    Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(
            @NotNull DeleteTenantDeploymentDeveloperRequest request);

}
