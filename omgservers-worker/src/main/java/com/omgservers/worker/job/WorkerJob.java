package com.omgservers.worker.job;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.worker.WorkerApplication;
import com.omgservers.worker.component.HandlerHolder;
import com.omgservers.worker.component.TokenHolder;
import com.omgservers.worker.module.service.ServiceModule;
import com.omgservers.worker.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduler;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WorkerJob {

    static final String JOB_NAME = "worker";
    static final String JOB_INTERVAL = "1s";

    final ServiceModule serviceModule;

    final GetConfigOperation getConfigOperation;

    final HandlerHolder handlerHolder;
    final TokenHolder tokenHolder;
    final Scheduler scheduler;

    @WithSpan
    void startUp(@Observes @Priority(WorkerApplication.START_UP_WORKER_JOB_PRIORITY) StartupEvent event) {
        final var trigger = scheduler.newJob(JOB_NAME)
                .setInterval(JOB_INTERVAL)
                .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                .setAsyncTask(scheduledExecution -> workerJobTask()
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.info("Worker job failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                            return null;
                        }))
                .schedule();
        log.info("Worker was scheduled, {}", trigger);
    }

    Uni<Void> workerJobTask() {
        final var token = tokenHolder.getToken();
        return viewRuntimeCommands(token)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(runtimeCommands -> {
                    final var handler = handlerHolder.getHandler();
                    return Uni.createFrom().voidItem()
                            .map(voidItem -> handler.handleCommands(runtimeCommands))
                            .flatMap(doCommands -> {
                                final var runtimeCommandsIds = runtimeCommands.stream()
                                        .map(RuntimeCommandModel::getId)
                                        .toList();
                                return handleRuntimeCommands(token, runtimeCommandsIds, doCommands);
                            })
                            .replaceWithVoid();
                })
                .replaceWithVoid();
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final String token) {
        final var runtimeId = getConfigOperation.getConfig().runtimeId();
        final var request = new ViewRuntimeCommandsWorkerRequest(runtimeId);
        return serviceModule.getWorkerService().viewRuntimeCommands(request, token)
                .map(ViewRuntimeCommandsWorkerResponse::getRuntimeCommands);
    }

    Uni<Boolean> handleRuntimeCommands(final String token,
                                       final List<Long> runtimeCommandIds,
                                       final List<DoCommandModel> doCommands) {
        final var runtimeId = getConfigOperation.getConfig().runtimeId();
        final var request = new HandleRuntimeCommandsWorkerRequest(runtimeId,
                runtimeCommandIds,
                doCommands);
        return serviceModule.getWorkerService().handleRuntimeCommands(request, token)
                .map(HandleRuntimeCommandsWorkerResponse::getHandled);
    }
}
