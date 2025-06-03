package com.omgservers.api;

import com.omgservers.api.configuration.OpenApiConfiguration;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorRequest;
import com.omgservers.schema.entrypoint.connector.CreateTokenConnectorResponse;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorRequest;
import com.omgservers.schema.entrypoint.connector.InterchangeMessagesConnectorResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Connector Entrypoint API")
@Path("/service/v1/entrypoint/connector/request")
@SecurityRequirement(name = OpenApiConfiguration.CONNECTOR_SECURITY_SCHEMA)
public interface ConnectorApi {

    @POST
    @Path("/create-token")
    Uni<CreateTokenConnectorResponse> execute(@NotNull CreateTokenConnectorRequest request);

    @POST
    @Path("/interchange-messages")
    Uni<InterchangeMessagesConnectorResponse> execute(@NotNull InterchangeMessagesConnectorRequest request);
}
