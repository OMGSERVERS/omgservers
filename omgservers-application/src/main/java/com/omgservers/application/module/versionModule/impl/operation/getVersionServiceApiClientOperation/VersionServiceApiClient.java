package com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation;

import com.omgservers.application.module.securityModule.impl.headersFactory.ServiceAccountClientHeadersFactory;
import com.omgservers.application.exception.ClientSideExceptionMapper;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceAccountClientHeadersFactory.class)
public interface VersionServiceApiClient extends VersionServiceApi {
}
