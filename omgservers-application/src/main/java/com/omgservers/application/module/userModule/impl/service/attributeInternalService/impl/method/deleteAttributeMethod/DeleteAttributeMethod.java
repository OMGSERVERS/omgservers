package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod;

import com.omgservers.dto.userModule.DeleteAttributeInternalRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteAttributeMethod {
    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);
}
