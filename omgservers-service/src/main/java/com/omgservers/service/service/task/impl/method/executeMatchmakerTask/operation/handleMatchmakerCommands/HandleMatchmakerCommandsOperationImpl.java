package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import com.omgservers.service.service.task.impl.method.executeMatchmakerTask.dto.HandleMatchmakerResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleMatchmakerCommandsOperationImpl implements HandleMatchmakerCommandsOperation {

    final Map<MatchmakerCommandQualifierEnum, MatchmakerCommandHandler> matchmakerCommandHandlers;

    HandleMatchmakerCommandsOperationImpl(final Instance<MatchmakerCommandHandler> matchmakerCommandHandlerBeans) {
        this.matchmakerCommandHandlers = new ConcurrentHashMap<>();
        matchmakerCommandHandlerBeans.stream().forEach(matchmakerCommandHandler -> {
            final var qualifier = matchmakerCommandHandler.getQualifier();
            if (matchmakerCommandHandlers.put(qualifier, matchmakerCommandHandler) != null) {
                log.error("Multiple \"{}\" handlers were detected", qualifier);
            }
        });
    }

    @Override
    public void execute(final FetchMatchmakerResult fetchMatchmakerResult,
                        final HandleMatchmakerResult handleMatchmakerResult) {
        fetchMatchmakerResult.matchmakerState().getMatchmakerCommands()
                .forEach(matchmakerCommand -> {
                    final var qualifier = matchmakerCommand.getQualifier();
                    final var qualifierBodyClass = qualifier.getBodyClass();
                    final var body = matchmakerCommand.getBody();
                    final var commandId = matchmakerCommand.getId();

                    if (!qualifierBodyClass.isInstance(body)) {
                        log.error("Qualifier \"{}\" and body class \"{}\" do not match, id={}",
                                qualifier, body.getClass().getSimpleName(), commandId);
                        return;
                    }

                    if (!matchmakerCommandHandlers.containsKey(qualifier)) {
                        log.error("Handler for \"{}\" not found, id={}",
                                qualifier, commandId);
                        return;
                    }

                    final var handled = matchmakerCommandHandlers.get(qualifier).handle(fetchMatchmakerResult,
                            handleMatchmakerResult,
                            matchmakerCommand);

                    if (handled) {
                        handleMatchmakerResult.matchmakerChangeOfState().getMatchmakerCommandsToDelete()
                                .add(matchmakerCommand.getId());
                    }
                });

    }
}
