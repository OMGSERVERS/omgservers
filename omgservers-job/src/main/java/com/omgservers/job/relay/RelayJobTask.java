package com.omgservers.job.relay;

import com.omgservers.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.dto.internal.ViewEventsForRelayResponse;
import com.omgservers.model.eventProjection.EventProjectionModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.jobService.impl.JobTask;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.amqp.OutgoingAmqpMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.List;

@Slf4j
@ApplicationScoped
public class RelayJobTask implements JobTask {
    private static final int VIEW_LIMIT = 1024;

    final SystemModule systemModule;

    final MutinyEmitter<Long> outboxEventsEmitter;

    public RelayJobTask(SystemModule systemModule,
                        @Channel("outbox-events") MutinyEmitter<Long> outboxEventsEmitter) {
        this.systemModule = systemModule;
        this.outboxEventsEmitter = outboxEventsEmitter;
    }

    @Override
    public JobQualifierEnum getJobType() {
        return JobQualifierEnum.RELAY;
    }

    @Override
    public Uni<Void> executeTask(final Long shardKey, final Long entityId) {
        return relayBatchOfEvents()
                .repeat().until(relayedEventsCount -> relayedEventsCount == 0)
                .collect().last().replaceWithVoid();
    }

    Uni<Integer> relayBatchOfEvents() {
        return viewEventProjections()
                .flatMap(eventProjections -> {
                    final var size = eventProjections.size();
                    if (size > 0) {
                        return relayEvents(eventProjections)
                                .flatMap(voidItem -> {
                                    final var ids = eventProjections.stream()
                                            .map(EventProjectionModel::getId)
                                            .toList();
                                    return updateEventsRelayedFlag(ids);
                                })
                                .replaceWith(size);
                    } else {
                        return Uni.createFrom().item(size);
                    }
                });
    }

    Uni<List<EventProjectionModel>> viewEventProjections() {
        final var request = new ViewEventsForRelayRequest(VIEW_LIMIT);
        return systemModule.getEventService().viewEventsForRelay(request)
                .map(ViewEventsForRelayResponse::getEventProjections);
    }

    Uni<Void> relayEvents(List<EventProjectionModel> eventProjections) {
        return Multi.createFrom().iterable(eventProjections)
                .onItem().transformToUniAndConcatenate(eventProjection -> {
                    final var metadata = OutgoingAmqpMetadata.builder()
                            .withGroupId(String.valueOf(eventProjection.getGroupId()))
                            .build();
                    final var message = Message.of(eventProjection.getId())
                            .addMetadata(metadata);
                    return outboxEventsEmitter.sendMessage(message);
                })
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateEventsRelayedFlag(List<Long> ids) {
        final var request = new UpdateEventsRelayedFlagRequest(ids, true);
        return systemModule.getEventService().updateEventsRelayedFlag(request)
                .replaceWithVoid();
    }
}
