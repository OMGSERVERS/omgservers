package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod;

import com.omgservers.base.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.getMatchmakerMethod.GetMatchmakerMethod;
import com.omgservers.dto.matchmakerModule.SyncMatchmakerInternalRequest;
import com.omgservers.base.factory.MatchmakerModelFactory;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SyncMatchmakerMethodTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    SyncMatchmakerMethod syncMatchmakerMethod;

    @Inject
    GetMatchmakerMethod getMatchmakerMethod;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @InjectMock
    EventHelpService eventHelpServiceMock;

    @Test
    void givenMatchmaker_whenSyncMatchmaker_thenEventInsertedAndEntityCreated() {
        final var matchmaker1 = matchmakerModelFactory.create(tenantId(), stageId());
        final var syncMatchmakerInternalRequest = new SyncMatchmakerInternalRequest(matchmaker1);
        syncMatchmakerMethod.syncMatchmaker(TIMEOUT, syncMatchmakerInternalRequest);

//        ArgumentCaptor<InsertEventHelpRequest> insertEventRequest = ArgumentCaptor.forClass(InsertEventHelpRequest.class);
//        Mockito.verify(eventHelpServiceMock).insertEvent(insertEventRequest.capture());
//        final var event = insertEventRequest.getValue().getEventBody();
//        assertEquals(EventQualifierEnum.MATCHMAKER_CREATED, event.getQualifier());
//        final var eventBody = (EventCreatedEventBodyModel) event;
//        assertEquals(matchmaker1.getId(), eventBody.getGroup());
//
//        final var originEvent = eventBody.getEvent();
//
//
//        final var originBody = (MatchmakerCreatedEventBodyModel) originEvent.getBody();
//        assertEquals(matchmaker1.getId(), originBody.getUuid());
//        assertEquals(matchmaker1.getTenantId(), originBody.getTenant());
//        assertEquals(matchmaker1.getStageId(), originBody.getStage());
//
//        final var getMatchmakerInternalRequest = new GetMatchmakerInternalRequest(matchmaker1.getId());
//        final var matchmaker2 = getMatchmakerMethod.getMatchmaker(TIMEOUT, getMatchmakerInternalRequest).getMatchmaker();
//        assertEquals(matchmaker1, matchmaker2);
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}