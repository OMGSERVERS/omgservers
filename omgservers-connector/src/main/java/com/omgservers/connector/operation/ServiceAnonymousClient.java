package com.omgservers.connector.operation;

import com.omgservers.api.ConnectorApi;
import com.omgservers.connector.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
public interface ServiceAnonymousClient extends ConnectorApi {
}
