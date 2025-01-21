
package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.schema.entrypoint.admin.BcryptHashAdminRequest;
import com.omgservers.schema.entrypoint.admin.BcryptHashAdminResponse;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminRequest;
import com.omgservers.schema.entrypoint.admin.CalculateShardAdminResponse;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminRequest;
import com.omgservers.schema.entrypoint.admin.GenerateIdAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Admin Entrypoint API")
@Path("/service/v1/entrypoint/admin/request")
public interface AdminApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenAdminResponse> execute(@NotNull CreateTokenAdminRequest request);

    @POST
    @Path("/generate-id")
    Uni<GenerateIdAdminResponse> execute(@NotNull GenerateIdAdminRequest request);

    @POST
    @Path("/bcrypt-hash")
    Uni<BcryptHashAdminResponse> execute(@NotNull BcryptHashAdminRequest request);

    @POST
    @Path("/calculate-shard")
    Uni<CalculateShardAdminResponse> execute(@NotNull CalculateShardAdminRequest request);

    @POST
    @Path("/ping-docker-host")
    Uni<PingDockerHostAdminResponse> execute(@NotNull PingDockerHostAdminRequest request);
}
