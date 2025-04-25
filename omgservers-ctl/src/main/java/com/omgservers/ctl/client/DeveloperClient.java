package com.omgservers.ctl.client;

import com.omgservers.api.DeveloperApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ClientHeadersFactory.class)
public interface DeveloperClient extends DeveloperApi {
}
