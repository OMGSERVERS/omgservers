package com.omgservers.service.master.task.impl.operation;

import com.omgservers.service.exception.ClientSideExceptionMapper;
import com.omgservers.service.master.task.impl.service.webService.impl.api.TaskApi;
import com.omgservers.service.security.ServiceHeadersFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;

@RegisterProvider(ClientSideExceptionMapper.class)
@RegisterClientHeaders(ServiceHeadersFactory.class)
public interface TaskMasterClient extends TaskApi {
}
