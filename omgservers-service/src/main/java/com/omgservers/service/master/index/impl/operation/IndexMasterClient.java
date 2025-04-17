package com.omgservers.service.master.index.impl.operation;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.master.index.impl.service.webService.impl.api.IndexApi;
import com.omgservers.service.security.ServiceHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface IndexMasterClient extends IndexApi {
}
