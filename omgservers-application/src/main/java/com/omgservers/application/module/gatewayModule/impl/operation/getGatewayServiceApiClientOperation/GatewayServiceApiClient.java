package com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.impl.serviceApi.GatewayServiceApi;
import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface GatewayServiceApiClient extends GatewayServiceApi {
}
