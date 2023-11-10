package com.omgservers.worker.job;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import com.omgservers.model.workerContext.WorkerContextModel;
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
                            log.warn("Worker job failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                            return null;
                        }))
                .schedule();
        log.info("Worker was scheduled, {}", trigger);
    }

    Uni<Void> workerJobTask() {
        final var runtimeId = getConfigOperation.getConfig().runtimeId();
        final var token = tokenHolder.getToken();
        return getWorkerContext(runtimeId, token)
                .invoke(workerContext -> log.info("Worker context, {}", workerContext))
                .flatMap(workerContext -> {
                    final var handler = handlerHolder.getHandler();
                    return Uni.createFrom().voidItem()
                            .map(voidItem -> handler.handleCommands(workerContext))
                            .flatMap(doCommands -> doWorkerCommand(runtimeId, doCommands, token))
                            .replaceWithVoid();
                })
                .replaceWithVoid();
    }

    Uni<Boolean> doWorkerCommand(final Long runtimeId,
                                 final List<DoCommandModel> doCommands,
                                 final String token) {
        final var request = new DoWorkerCommandsWorkerRequest(runtimeId, doCommands);
        return serviceModule.getWorkerService().doWorkerCommands(request, token)
                .map(DoWorkerCommandsWorkerResponse::getDone);
    }

    Uni<WorkerContextModel> getWorkerContext(final Long runtimeId, final String token) {
        final var request = new GetWorkerContextWorkerRequest(runtimeId);
        return serviceModule.getWorkerService().getWorkerContext(request, token)
                .map(GetWorkerContextWorkerResponse::getWorkerContext);
    }
}
