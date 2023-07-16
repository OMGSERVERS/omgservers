package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod;

import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.DeleteAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteAttributeMethod {
    Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request);
}
