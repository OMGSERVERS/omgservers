package com.omgservers.router.integration.impl.operation;

import com.omgservers.router.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceClientHeadersFactory.class)
public interface ServiceClient extends ServiceApi {
}
