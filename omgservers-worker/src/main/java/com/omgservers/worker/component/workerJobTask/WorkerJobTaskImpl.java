package com.omgservers.worker.component.workerJobTask;

import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.InterchangeWorkerRequest;
import com.omgservers.model.dto.worker.InterchangeWorkerResponse;
import com.omgservers.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.worker.component.handlerHolder.HandlerContainer;
import com.omgservers.worker.component.handlerHolder.HandlerHolder;
import com.omgservers.worker.component.tokenHolder.TokenHolder;
import com.omgservers.worker.exception.HandlerIsNotReadyYetException;
import com.omgservers.worker.exception.TokenIsNotReadyYetException;
import com.omgservers.worker.module.handler.HandlerModule;
import com.omgservers.worker.module.handler.lua.LuaHandlerModuleFactory;
import com.omgservers.worker.module.service.ServiceModule;
import com.omgservers.worker.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WorkerJobTaskImpl implements WorkerJobTask {

    final ServiceModule serviceModule;

    final GetConfigOperation getConfigOperation;

    final LuaHandlerModuleFactory luaHandlerModuleFactory;
    final HandlerContainer handlerContainer;
    final WorkerJobState workerJobState;
    final HandlerHolder handlerHolder;
    final TokenHolder tokenHolder;

    @Override
    public Uni<Void> executeTask() {
        try {
            final var token = tokenHolder.getToken();
            final var runtimeId = getConfigOperation.getWorkerConfig().runtimeId();
            try {
                final var handler = handlerHolder.getHandler();
                return useHandler(runtimeId, handler, token);
            } catch (HandlerIsNotReadyYetException e) {
                return bootstrapHandler(runtimeId, token);
            }
        } catch (TokenIsNotReadyYetException e) {
            log.warn(e.getMessage());
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Void> useHandler(final Long runtimeId,
                         final HandlerModule handler,
                         final String token) {
        final var workerOutgoingCommands = workerJobState.getOutgoingCommands();
        final var workerConsumedCommands = workerJobState.getConsumedCommands();

        return interchange(runtimeId, workerOutgoingCommands, workerConsumedCommands, token)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(incomingCommands -> {
                    log.info("Incoming commands, {}", incomingCommands);

                    final var outgoingCommands = handler.handleCommands(incomingCommands);
                    workerJobState.setOutgoingCommands(outgoingCommands);

                    final var incomingCommandsIds = incomingCommands.stream()
                            .map(RuntimeCommandModel::getId)
                            .toList();
                    workerJobState.setConsumedCommands(incomingCommandsIds);
                })
                .replaceWithVoid();
    }

    Uni<Void> bootstrapHandler(final Long runtimeId, final String token) {
        return getVersion(runtimeId, token)
                .invoke(this::buildHandler)
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long runtimeId, final String token) {
        final var request = new GetVersionWorkerRequest(runtimeId);
        return serviceModule.getWorkerService().getVersion(request, token)
                .map(GetVersionWorkerResponse::getVersion);
    }

    Uni<List<RuntimeCommandModel>> interchange(final Long runtimeId,
                                               final List<OutgoingCommandModel> outgoingCommands,
                                               final List<Long> consumedCommands,
                                               final String token) {
        final var request = new InterchangeWorkerRequest(runtimeId, outgoingCommands, consumedCommands);
        return serviceModule.getWorkerService().interchange(request, token)
                .map(InterchangeWorkerResponse::getIncomingCommands);
    }

    void buildHandler(VersionModel version) {
        // TODO: detect type of worker by version fields
        final var handlerModule = luaHandlerModuleFactory.build(version);
        handlerContainer.set(handlerModule);
        log.info("Lua handler was built for versionId={}", version.getId());
    }
}
