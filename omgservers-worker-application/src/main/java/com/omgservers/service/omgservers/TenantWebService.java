package com.omgservers.service.omgservers;

import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/omgservers/tenant-api/v1/request")
@RegisterRestClient(configKey = "omgservers")
public interface TenantWebService {

    @PUT
    @Path("/get-version")
    Uni<GetVersionResponse> getVersion(GetVersionRequest request);
}
