package com.omgservers.api;

import com.omgservers.api.configuration.OpenApiConfiguration;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Developer Entrypoint API")
@Path("/service/v1/entrypoint/developer/request")
@SecurityRequirement(name = OpenApiConfiguration.DEVELOPER_SECURITY_SCHEMA)
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
    Uni<CreateProjectDeveloperResponse> execute(@NotNull CreateProjectDeveloperRequest request);

    @POST
    @Path("/create-project-alias")
    Uni<CreateProjectAliasDeveloperResponse> execute(@NotNull CreateProjectAliasDeveloperRequest request);

    @POST
    @Path("/get-project-details")
    Uni<GetProjectDetailsDeveloperResponse> execute(@NotNull GetProjectDetailsDeveloperRequest request);

    @POST
    @Path("/delete-project")
    Uni<DeleteProjectDeveloperResponse> execute(@NotNull DeleteProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    @POST
    @Path("/create-stage")
    Uni<CreateStageDeveloperResponse> execute(@NotNull CreateStageDeveloperRequest request);

    @POST
    @Path("/create-stage-alias")
    Uni<CreateStageAliasDeveloperResponse> execute(@NotNull CreateStageAliasDeveloperRequest request);

    @POST
    @Path("/get-stage-details")
    Uni<GetStageDetailsDeveloperResponse> execute(@NotNull GetStageDetailsDeveloperRequest request);

    @POST
    @Path("/delete-stage")
    Uni<DeleteStageDeveloperResponse> execute(@NotNull DeleteStageDeveloperRequest request);

    /*
    Tenant version
     */

    @POST
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> execute(@NotNull CreateVersionDeveloperRequest request);

    @POST
    @Path("/get-version-details")
    Uni<GetVersionDetailsDeveloperResponse> execute(@NotNull GetVersionDetailsDeveloperRequest request);

    @POST
    @Path("/delete-version")
    Uni<DeleteVersionDeveloperResponse> execute(@NotNull DeleteVersionDeveloperRequest request);

    /*
    Tenant image
     */

    @POST
    @Path("/create-image")
    Uni<CreateImageDeveloperResponse> execute(@NotNull CreateImageDeveloperRequest request);

    /*
    Tenant deployment
     */

    @POST
    @Path("/create-deployment")
    Uni<CreateDeploymentDeveloperResponse> execute(@NotNull CreateDeploymentDeveloperRequest request);

    @POST
    @Path("/get-deployment-details")
    Uni<GetDeploymentDetailsDeveloperResponse> execute(@NotNull GetDeploymentDetailsDeveloperRequest request);

    @POST
    @Path("/delete-deployment")
    Uni<DeleteDeploymentDeveloperResponse> execute(@NotNull DeleteDeploymentDeveloperRequest request);

}
