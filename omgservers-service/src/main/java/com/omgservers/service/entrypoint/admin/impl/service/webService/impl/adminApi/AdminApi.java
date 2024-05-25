package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.model.dto.admin.CreateSupportAdminRequest;
import com.omgservers.model.dto.admin.CreateSupportAdminResponse;
import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Admin Entrypoint API")
@Path("/omgservers/v1/entrypoint/admin/request")
public interface AdminApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenAdminResponse> createToken(CreateTokenAdminRequest request);

    @PUT
    @Path("/create-support")
    Uni<CreateSupportAdminResponse> createSupport(CreateSupportAdminRequest request);

}
