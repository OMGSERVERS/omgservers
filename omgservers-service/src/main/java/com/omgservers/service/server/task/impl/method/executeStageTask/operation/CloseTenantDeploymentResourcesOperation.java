package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface CloseTenantDeploymentResourcesOperation {
    Uni<Void> execute(List<TenantDeploymentResourceModel> tenantDeploymentResources);
}
