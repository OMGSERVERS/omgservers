package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
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

    @PUT
    @Path("/get-tenant-dashboard")
    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(@NotNull GetTenantDashboardDeveloperRequest request);

    @PUT
    @Path("/create-project")
    Uni<CreateProjectDeveloperResponse> createProject(@NotNull CreateProjectDeveloperRequest request);

    @PUT
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> createVersion(@NotNull CreateVersionDeveloperRequest request);

    @PUT
    @Path("upload-version")
    Uni<UploadVersionDeveloperResponse> uploadVersion(@RestForm("tenantId") Long tenantId,
                                                      @RestForm("stageId") Long stageId,
                                                      @RestForm(FileUpload.ALL) List<FileUpload> files);

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionDeveloperResponse> deleteVersion(@NotNull DeleteVersionDeveloperRequest request);
}
