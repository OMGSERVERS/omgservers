package com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.internalModule.impl.service.internalWebService.impl.serviceApi.InternalServiceApi;
import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface InternalsServiceApiClient extends InternalServiceApi {
}
