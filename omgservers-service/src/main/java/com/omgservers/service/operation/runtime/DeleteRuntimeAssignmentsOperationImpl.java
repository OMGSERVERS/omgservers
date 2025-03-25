package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.service.shard.runtime.RuntimeShard;
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
class DeleteRuntimeAssignmentsOperationImpl implements DeleteRuntimeAssignmentsOperation {

    final RuntimeShard runtimeShard;

    @Override
    public Uni<Void> execute(final Long runtimeId) {
        return viewRuntimeAssignments(runtimeId)
                .flatMap(runtimeAssignments -> Multi.createFrom().iterable(runtimeAssignments)
                        .onItem().transformToUniAndConcatenate(runtimeAssignment -> {
                            final var runtimeAssignmentId = runtimeAssignment.getId();
                            return deleteRuntimeAssignments(runtimeId, runtimeAssignmentId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                runtimeId,
                                                runtimeAssignmentId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignments(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
    }

    Uni<Boolean> deleteRuntimeAssignments(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeAssignmentRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(DeleteRuntimeAssignmentResponse::getDeleted);
    }
}
