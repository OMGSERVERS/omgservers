package com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation;

import com.omgservers.base.impl.service.internalWebService.impl.serviceApi.InternalServiceApi;
import com.omgservers.base.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface InternalsServiceApiClient extends InternalServiceApi {
}
