package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceClient;

import com.omgservers.dispatcher.component.DispatcherHeadersFactory;
import com.omgservers.dispatcher.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(DispatcherHeadersFactory.class)
public interface ServiceModuleClient extends ServiceApi {
}
