package com.omgservers.module.version.impl.operation.getVersionModuleClient;

import com.omgservers.module.security.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.module.version.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import com.omgservers.exception.ClientSideExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface VersionModuleClient extends VersionServiceApi {
}
