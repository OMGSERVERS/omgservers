package com.omgservers.service.module.alias.impl.operation.getAliasModuleClient;

import com.omgservers.service.component.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.module.alias.impl.service.webService.impl.api.AliasApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface AliasModuleClient extends AliasApi {
}
