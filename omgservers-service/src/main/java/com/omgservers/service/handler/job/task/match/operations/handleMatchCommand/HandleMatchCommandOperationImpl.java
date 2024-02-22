package com.omgservers.service.handler.job.task.match.operations.handleMatchCommand;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleMatchCommandOperationImpl implements HandleMatchCommandOperation {

    final Map<MatchCommandQualifierEnum, MatchCommandHandler> matchCommandHandlers;

    HandleMatchCommandOperationImpl(final Instance<MatchCommandHandler> matchCommandHandlerBeans) {
        this.matchCommandHandlers = new ConcurrentHashMap<>();
        matchCommandHandlerBeans.stream().forEach(matchCommandHandler -> {
            final var qualifier = matchCommandHandler.getQualifier();
            if (matchCommandHandlers.put(qualifier, matchCommandHandler) != null) {
                log.error("Multiple match command handlers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> handleMatchCommand(MatchCommandModel matchCommand) {
        final var qualifier = matchCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();
        final var body = matchCommand.getBody();

        if (!qualifierBodyClass.isInstance(body)) {
            log.error("Qualifier and match command body do not match, qualifier={}, bodyClass={}, id={}",
                    matchCommand.getQualifier(), body.getClass(), matchCommand.getId());
            return Uni.createFrom().voidItem();
        }

        if (!matchCommandHandlers.containsKey(qualifier)) {
            log.error("Match command handler was not found, qualifier={}, id={}",
                    matchCommand.getQualifier(), matchCommand.getId());
            return Uni.createFrom().voidItem();
        }

        return matchCommandHandlers.get(qualifier)
                .handle(matchCommand);
    }
}
