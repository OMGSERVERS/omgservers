package com.omgservers.dispatcher.service.service.impl.method.operation.getServiceAnonymousClient;

import com.omgservers.dispatcher.component.AnonymousHeadersFactory;
import com.omgservers.dispatcher.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(AnonymousHeadersFactory.class)
public interface ServiceAnonymousModuleClient extends ServiceAnonymousApi {
}
