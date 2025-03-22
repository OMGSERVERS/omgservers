package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

import com.omgservers.schema.entrypoint.developer.*;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Tag(name = "Developer Entrypoint API")
@Path("/service/v1/entrypoint/developer/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.DEVELOPER_SECURITY_SCHEMA)
public interface DeveloperApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> execute(@NotNull CreateTokenDeveloperRequest request);

    /*
    Tenant
     */

    @POST
    @Path("/get-tenant-details")
    Uni<GetTenantDetailsDeveloperResponse> execute(@NotNull GetTenantDetailsDeveloperRequest request);

    /*
    Tenant project
     */

    @POST
    @Path("/create-project")
    Uni<CreateTenantProjectDeveloperResponse> execute(@NotNull CreateTenantProjectDeveloperRequest request);

    @POST
    @Path("/create-project-alias")
    Uni<CreateTenantProjectAliasDeveloperResponse> execute(@NotNull CreateTenantProjectAliasDeveloperRequest request);

    @POST
    @Path("/get-project-details")
    Uni<GetTenantProjectDetailsDeveloperResponse> execute(@NotNull GetTenantProjectDetailsDeveloperRequest request);

    @POST
    @Path("/delete-project")
    Uni<DeleteTenantProjectDeveloperResponse> execute(@NotNull DeleteTenantProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    @POST
    @Path("/create-stage")
    Uni<CreateTenantStageDeveloperResponse> execute(@NotNull CreateTenantStageDeveloperRequest request);

    @POST
    @Path("/create-stage-alias")
    Uni<CreateTenantStageAliasDeveloperResponse> execute(@NotNull CreateTenantStageAliasDeveloperRequest request);

    @POST
    @Path("/get-stage-details")
    Uni<GetTenantStageDetailsDeveloperResponse> execute(@NotNull GetTenantStageDetailsDeveloperRequest request);

    @POST
    @Path("/delete-stage")
    Uni<DeleteTenantStageDeveloperResponse> execute(@NotNull DeleteTenantStageDeveloperRequest request);

    /*
    Tenant version
     */

    @POST
    @Path("/create-version")
    Uni<CreateTenantVersionDeveloperResponse> execute(@NotNull CreateTenantVersionDeveloperRequest request);

    @POST
    @Path("/upload-files-archive")
    Uni<UploadFilesArchiveDeveloperResponse> execute(@RestForm("tenant") String tenant,
                                                     @RestForm("tenantVersionId") Long tenantVersionId,
                                                     @RestForm(FileUpload.ALL) List<FileUpload> files);

    @POST
    @Path("/get-version-details")
    Uni<GetTenantVersionDetailsDeveloperResponse> execute(@NotNull GetTenantVersionDetailsDeveloperRequest request);

    @POST
    @Path("/delete-version")
    Uni<DeleteTenantVersionDeveloperResponse> execute(@NotNull DeleteTenantVersionDeveloperRequest request);

    /*
    Tenant image
     */

    @POST
    @Path("/create-image")
    Uni<CreateTenantImageDeveloperResponse> execute(@NotNull CreateTenantImageDeveloperRequest request);

    /*
    Tenant deployment
     */

    @POST
    @Path("/deploy-version")
    Uni<DeployTenantVersionDeveloperResponse> execute(@NotNull DeployTenantVersionDeveloperRequest request);

    @POST
    @Path("/get-deployment-details")
    Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            @NotNull GetTenantDeploymentDetailsDeveloperRequest request);

    @POST
    @Path("/delete-deployment")
    Uni<DeleteTenantDeploymentDeveloperResponse> execute(@NotNull DeleteTenantDeploymentDeveloperRequest request);

    @POST
    @Path("/create-lobby-request")
    Uni<CreateLobbyRequestDeveloperResponse> execute(@NotNull CreateLobbyRequestDeveloperRequest request);

    @POST
    @Path("/delete-lobby")
    Uni<DeleteLobbyDeveloperResponse> execute(@NotNull DeleteLobbyDeveloperRequest request);

    @POST
    @Path("/create-matchmaker-request")
    Uni<CreateMatchmakerRequestDeveloperResponse> execute(@NotNull CreateMatchmakerRequestDeveloperRequest request);

    @POST
    @Path("/delete-matchmaker")
    Uni<DeleteMatchmakerDeveloperResponse> execute(@NotNull DeleteMatchmakerDeveloperRequest request);

}
