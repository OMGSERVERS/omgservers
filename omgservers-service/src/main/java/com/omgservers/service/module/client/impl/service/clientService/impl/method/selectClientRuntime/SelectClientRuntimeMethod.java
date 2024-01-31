package com.omgservers.service.module.client.impl.service.clientService.impl.method.selectClientRuntime;

import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface SelectClientRuntimeMethod {
    Uni<SelectClientRuntimeResponse> selectClientRuntime(SelectClientRuntimeRequest request);
}
