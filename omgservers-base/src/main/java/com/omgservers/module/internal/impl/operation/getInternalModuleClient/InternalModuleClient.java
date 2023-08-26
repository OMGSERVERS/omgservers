package com.omgservers.module.internal.impl.operation.getInternalModuleClient;

import com.omgservers.module.internal.impl.service.internalWebService.impl.serviceApi.InternalServiceApi;
import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface InternalModuleClient extends InternalServiceApi {
}
