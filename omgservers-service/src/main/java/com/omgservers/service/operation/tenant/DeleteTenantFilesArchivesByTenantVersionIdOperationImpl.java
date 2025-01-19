package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.DeleteTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.ViewTenantFilesArchivesResponse;
import com.omgservers.service.exception.ServerSideClientException;
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
class DeleteTenantFilesArchivesByTenantVersionIdOperationImpl
        implements DeleteTenantFilesArchivesByTenantVersionIdOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantVersionId) {
        return viewTenantFilesArchives(tenantId, tenantVersionId)
                .flatMap(tenantFilesArchives -> Multi.createFrom().iterable(tenantFilesArchives)
                        .onItem().transformToUniAndConcatenate(tenantFilesArchive ->
                                deleteTenantFilesArchive(tenantId, tenantFilesArchive.getId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete tenant files archive, " +
                                                            "tenantVersion={}/{}, " +
                                                            "tenantFilesArchiveId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantVersionId,
                                                    tenantFilesArchive.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantFilesArchiveProjectionModel>> viewTenantFilesArchives(final Long tenantId,
                                                                         final Long tenantVersionId) {
        final var request = new ViewTenantFilesArchivesRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantFilesArchives(request)
                .map(ViewTenantFilesArchivesResponse::getTenantFilesArchives);
    }

    Uni<Boolean> deleteTenantFilesArchive(final Long tenantId, final Long id) {
        final var request = new DeleteTenantFilesArchiveRequest(tenantId, id);
        return tenantModule.getService().deleteTenantFilesArchive(request)
                .map(DeleteTenantFilesArchiveResponse::getDeleted);
    }
}
