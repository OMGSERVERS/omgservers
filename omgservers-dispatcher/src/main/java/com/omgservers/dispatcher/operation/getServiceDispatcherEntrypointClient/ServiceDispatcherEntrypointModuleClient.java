package com.omgservers.dispatcher.operation.getServiceDispatcherEntrypointClient;

import com.omgservers.dispatcher.component.DispatcherHeadersFactory;
import com.omgservers.dispatcher.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(DispatcherHeadersFactory.class)
public interface ServiceDispatcherEntrypointModuleClient extends ServiceDispatcherEntrypointApi {
}
