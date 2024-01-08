package com.omgservers.service.module.developer.impl.service.webService.impl.developerApi;

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
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Path("/omgservers/developer-api/v1/request")
public interface DeveloperApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    @PUT
    @Path("/get-tenant-dashboard")
    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(GetTenantDashboardDeveloperRequest request);

    @PUT
    @Path("/create-project")
    Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request);

    @PUT
    @Path("/create-version")
    Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request);

    @PUT
    @Path("upload-version")
    Uni<UploadVersionDeveloperResponse> uploadVersion(@RestForm("tenantId") Long tenantId,
                                                      @RestForm("stageId") Long stageId,
                                                      @RestForm(FileUpload.ALL) List<FileUpload> files);

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionDeveloperResponse> deleteVersion(DeleteVersionDeveloperRequest request);
}
