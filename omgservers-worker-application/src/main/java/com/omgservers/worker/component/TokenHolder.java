package com.omgservers.worker.component;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.worker.WorkerApplication;
import com.omgservers.worker.exception.WorkerStartUpException;
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

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenHolder {

    static final String JOB_NAME = "token-refresh";
    static final String JOB_INTERVAL = "60s";

    final ServiceModule serviceModule;

    final GetConfigOperation getConfigOperation;

    final TokenContainer tokenContainer;
    final Scheduler scheduler;

    public String getToken() {
        final var token = tokenContainer.get();
        if (Objects.isNull(token) || token.isBlank()) {
            throw new WorkerStartUpException("Token is not ready yet");
        }
        return token;
    }

    @WithSpan
    void startUp(@Observes @Priority(WorkerApplication.START_UP_TOKEN_HOLDER_PRIORITY) StartupEvent event) {
        refreshToken().await().indefinitely();
        final var trigger = scheduler.newJob(JOB_NAME)
                .setInterval(JOB_INTERVAL)
                .setDelayed(JOB_INTERVAL)
                .setConcurrentExecution(Scheduled.ConcurrentExecution.SKIP)
                .setAsyncTask(scheduledExecution -> refreshToken())
                .schedule();
        log.info("Token refresh was scheduled, {}", trigger);
    }

    Uni<Void> refreshToken() {
        return createToken()
                .invoke(tokenContainer::set)
                .replaceWithVoid()
                .onFailure()
                .recoverWithItem(t -> {
                    log.info("Token refresh failed, {}", t.getMessage());
                    return null;
                });
    }

    Uni<String> createToken() {
        final var userId = getConfigOperation.getConfig().userId();
        final var password = getConfigOperation.getConfig().password();
        final var request = new CreateTokenWorkerRequest(userId, password);
        return serviceModule.getWorkerService().createToken(request)
                .map(CreateTokenWorkerResponse::getRawToken)
                .map(token -> "Bearer " + token)
                .invoke(token -> log.info("Token was created"));
    }
}
