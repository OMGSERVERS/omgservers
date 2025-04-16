package com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient;

import com.omgservers.service.security.ServiceHeadersFactory;
import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.shard.deployment.impl.service.webService.impl.api.DeploymentApi;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface DeploymentModuleClient extends DeploymentApi {
}
