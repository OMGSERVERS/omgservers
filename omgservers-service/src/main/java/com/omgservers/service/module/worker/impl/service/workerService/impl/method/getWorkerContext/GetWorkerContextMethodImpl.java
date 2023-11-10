package com.omgservers.service.module.worker.impl.service.workerService.impl.method.getWorkerContext;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.workerContext.WorkerContextModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission.CheckRuntimePermissionOperation;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers.CollectPlayersOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetWorkerContextMethodImpl implements GetWorkerContextMethod {

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    final CheckRuntimePermissionOperation checkRuntimePermissionOperation;
    final CollectPlayersOperation collectPlayersOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetWorkerContextWorkerResponse> getWorkerContext(final GetWorkerContextWorkerRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var userId = securityIdentity.<Long>getAttribute("userId");

        return checkRuntimePermissionOperation.checkRuntimePermission(runtimeId,
                        userId,
                        RuntimePermissionEnum.HANDLE_RUNTIME)
                .flatMap(voidItem -> pollRuntimeCommands(runtimeId)
                        .flatMap(runtimeCommands -> collectPlayersOperation.collectPlayers(runtimeCommands)
                                .map(players -> new WorkerContextModel(runtimeCommands, players))
                        )
                )
                .map(GetWorkerContextWorkerResponse::new);
    }

    Uni<List<RuntimeCommandModel>> pollRuntimeCommands(final Long runtimeId) {
        // TODO: do it by one request
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> deleteRuntimeCommands(runtimeId, runtimeCommands)
                        .replaceWith(runtimeCommands));
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Boolean> deleteRuntimeCommands(final Long runtimeId,
                                       final List<RuntimeCommandModel> runtimeCommands) {
        List<Long> ids = runtimeCommands.stream().map(RuntimeCommandModel::getId).toList();
        if (ids.size() > 0) {
            final var request = new DeleteRuntimeCommandsRequest(runtimeId, ids);
            return runtimeModule.getRuntimeService().deleteRuntimeCommands(request)
                    .map(DeleteRuntimeCommandsResponse::getDeleted);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
