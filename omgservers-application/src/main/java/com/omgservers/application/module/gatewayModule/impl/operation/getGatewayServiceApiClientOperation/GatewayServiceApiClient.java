package com.omgservers.application.module.gatewayModule.impl.operation.getGatewayServiceApiClientOperation;

import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.impl.serviceApi.GatewayServiceApi;
import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface GatewayServiceApiClient extends GatewayServiceApi {
}
