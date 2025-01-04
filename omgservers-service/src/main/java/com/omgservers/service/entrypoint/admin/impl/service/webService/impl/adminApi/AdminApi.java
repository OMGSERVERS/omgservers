
package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Admin Entrypoint API")
@Path("/service/v1/entrypoint/admin/request")
public interface AdminApi {

    @PUT
    @Path("/create-token")
    Uni<CreateTokenAdminResponse> execute(@NotNull CreateTokenAdminRequest request);

    @PUT
    @Path("/calculate-shard")
    Uni<CalculateShardAdminResponse> execute(@NotNull CalculateShardAdminRequest request);

    @PUT
    @Path("/ping-docker-host")
    Uni<PingDockerHostAdminResponse> execute(@NotNull PingDockerHostAdminRequest request);
}
