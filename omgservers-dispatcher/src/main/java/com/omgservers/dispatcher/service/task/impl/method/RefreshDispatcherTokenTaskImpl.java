package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.component.DispatcherTokenContainer;
import com.omgservers.dispatcher.operation.GetDispatcherConfigOperation;
import com.omgservers.dispatcher.service.service.ServiceService;
import com.omgservers.dispatcher.service.service.dto.CreateTokenRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RefreshDispatcherTokenTaskImpl {

    final ServiceService serviceService;

    final GetDispatcherConfigOperation getDispatcherConfigOperation;

    final DispatcherTokenContainer dispatcherTokenContainer;

    public Uni<Boolean> execute() {
        final var alias = getDispatcherConfigOperation.getDispatcherConfig().dispatcherUser().alias();
        final var password = getDispatcherConfigOperation.getDispatcherConfig().dispatcherUser().password();

        final var request = new CreateTokenRequest(alias, password);
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> log.info("Creating dispatcher token, alias={}", alias))
                .flatMap(voidItem -> serviceService.execute(request))
                .map(response -> {
                    final var rawToken = response.getRawToken();
                    dispatcherTokenContainer.setToken(rawToken);

                    log.info("Dispatcher token was created");

                    return Boolean.TRUE;
                })
                .onFailure().recoverWithUni(t -> {
                    log.error("Token was not created, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .repeat().withDelay(Duration.ofSeconds(1)).whilst(Boolean.FALSE::equals)
                .collect().last();
    }
}
