package com.omgservers.module.script.impl.operation.getScriptModuleClient;

import com.omgservers.exception.ClientSideExceptionMapper;
import com.omgservers.module.script.impl.service.webService.impl.api.ScriptApi;
import com.omgservers.security.ServiceAccountClientHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface ScriptModuleClient extends ScriptApi {
}
