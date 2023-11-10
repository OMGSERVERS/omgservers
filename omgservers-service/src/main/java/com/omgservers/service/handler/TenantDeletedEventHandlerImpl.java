package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (TenantDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getDeletedTenant(tenantId)
                .flatMap(tenant -> {
                    log.info("Tenant was deleted, tenantId={}", tenantId);

                    return viewProjects(tenantId)
                            .flatMap(projects -> Multi.createFrom().iterable(projects)
                                    .onItem().transformToUniAndConcatenate(project ->
                                            deleteProject(tenantId, project.getId()))
                                    .collect().asList()
                                    .replaceWithVoid()
                            );
                })
                .replaceWith(true);
    }

    Uni<TenantModel> getDeletedTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getTenantService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<List<ProjectModel>> viewProjects(final Long tenantId) {
        final var request = new ViewProjectsRequest(tenantId);
        return tenantModule.getProjectService().viewProjects(request)
                .map(ViewProjectsResponse::getProjects);
    }

    Uni<Boolean> deleteProject(final Long tenantId, final Long id) {
        final var request = new DeleteProjectRequest(tenantId, id);
        return tenantModule.getProjectService().deleteProject(request)
                .map(DeleteProjectResponse::getDeleted);
    }
}
