package com.omgservers.connector.operation;

import com.omgservers.api.ConnectorApi;
import com.omgservers.connector.exception.ClientSideExceptionMapper;
import com.omgservers.connector.security.ConnectorHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ConnectorHeadersFactory.class)
public interface ServiceClient extends ConnectorApi {
}
