package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperResponse;
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
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
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
    Uni<CreateTokenDeveloperResponse> execute(@NotNull CreateTokenDeveloperRequest request);

    /*
    Tenant
     */

    @PUT
    @Path("/get-tenant-dashboard")
    Uni<GetTenantDashboardDeveloperResponse> execute(@NotNull GetTenantDashboardDeveloperRequest request);

    /*
    Tenant project
     */

    @PUT
    @Path("/create-project")
    Uni<CreateTenantProjectDeveloperResponse> execute(@NotNull CreateTenantProjectDeveloperRequest request);

    @PUT
    @Path("/get-project-dashboard")
    Uni<GetTenantProjectDashboardDeveloperResponse> execute(@NotNull GetTenantProjectDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-project")
    Uni<DeleteTenantProjectDeveloperResponse> execute(@NotNull DeleteTenantProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    @PUT
    @Path("/create-stage")
    Uni<CreateTenantStageDeveloperResponse> execute(@NotNull CreateTenantStageDeveloperRequest request);

    @PUT
    @Path("/get-stage-dashboard")
    Uni<GetTenantStageDashboardDeveloperResponse> execute(@NotNull GetTenantStageDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-stage")
    Uni<DeleteTenantStageDeveloperResponse> execute(@NotNull DeleteTenantStageDeveloperRequest request);

    /*
    Tenant version
     */

    @PUT
    @Path("/create-version")
    Uni<CreateTenantVersionDeveloperResponse> execute(@NotNull CreateTenantVersionDeveloperRequest request);

    @PUT
    @Path("/upload-files-archive")
    Uni<UploadFilesArchiveDeveloperResponse> execute(@RestForm("tenantId") Long tenantId,
                                                     @RestForm("tenantVersionId") Long tenantVersionId,
                                                     @RestForm(FileUpload.ALL) List<FileUpload> files);

    @PUT
    @Path("/get-version-dashboard")
    Uni<GetTenantVersionDashboardDeveloperResponse> execute(@NotNull GetTenantVersionDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-version")
    Uni<DeleteTenantVersionDeveloperResponse> execute(@NotNull DeleteTenantVersionDeveloperRequest request);

    /*
    Tenant deployment
     */

    @PUT
    @Path("/deploy-version")
    Uni<DeployTenantVersionDeveloperResponse> execute(@NotNull DeployTenantVersionDeveloperRequest request);

    @PUT
    @Path("/get-deployment-dashboard")
    Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(
            @NotNull GetTenantDeploymentDashboardDeveloperRequest request);

    @PUT
    @Path("/delete-deployment")
    Uni<DeleteTenantDeploymentDeveloperResponse> execute(@NotNull DeleteTenantDeploymentDeveloperRequest request);

    @PUT
    @Path("/create-lobby-request")
    Uni<CreateLobbyRequestDeveloperResponse> execute(@NotNull CreateLobbyRequestDeveloperRequest request);

    @PUT
    @Path("/delete-lobby")
    Uni<DeleteLobbyDeveloperResponse> execute(@NotNull DeleteLobbyDeveloperRequest request);

    @PUT
    @Path("/create-matchmaker-request")
    Uni<CreateMatchmakerRequestDeveloperResponse> execute(@NotNull CreateMatchmakerRequestDeveloperRequest request);

    @PUT
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerDeveloperResponse> execute(@NotNull DeleteMatchmakerDeveloperRequest request);

}
