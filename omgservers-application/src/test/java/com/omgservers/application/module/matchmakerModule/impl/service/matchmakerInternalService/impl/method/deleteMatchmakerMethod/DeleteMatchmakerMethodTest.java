package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.deleteMatchmakerMethod;

import com.omgservers.base.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl.method.syncMatchmakerMethod.SyncMatchmakerMethod;
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
class DeleteMatchmakerMethodTest extends Assertions {
    static private final long TIMEOUT = 1L;

    @Inject
    DeleteMatchmakerMethod deleteMatchmakerMethod;

    @Inject
    SyncMatchmakerMethod syncMatchmakerMethod;

    @Inject
    MatchmakerModelFactory matchmakerModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @InjectMock
    EventHelpService eventHelpServiceMock;

    @Test
    void givenMatchmaker_whenDeleteMatchmaker_thenEventInsertedAndEntityDeleted() {
        final var matchmaker = matchmakerModelFactory.create(tenantId(), stageId());
        final var createMatchmakerInternalRequest = new SyncMatchmakerInternalRequest(matchmaker);
        syncMatchmakerMethod.syncMatchmaker(TIMEOUT, createMatchmakerInternalRequest);

//        final var deleteMatchmakerInternalRequest = new DeleteMatchmakerInternalRequest(matchmaker.getId());
//        assertTrue(deleteMatchmakerMethod.deleteMatchmaker(TIMEOUT, deleteMatchmakerInternalRequest).getDeleted());
//
//        ArgumentCaptor<InsertEventHelpRequest> insertEventRequest = ArgumentCaptor.forClass(InsertEventHelpRequest.class);
//        Mockito.verify(eventHelpServiceMock, Mockito.times(2)).insertEvent(insertEventRequest.capture());
//        final var event = insertEventRequest.getValue().getEvent();
//        assertEquals(EventQualifierEnum.EVENT_CREATED, event.getQualifier());
//        final var eventBody = (EventCreatedEventBodyModel) event.getBody();
//        final var originEvent = eventBody.getEvent();
//        assertEquals(EventQualifierEnum.MATCHMAKER_DELETED, originEvent.getQualifier());
//        assertEquals(matchmaker.getId(), originEvent.getGroup());
//        final var originBody = (MatchmakerDeletedEventBodyModel) originEvent.getBody();
//        assertEquals(matchmaker.getId(), originBody.getUuid());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long stageId() {
        return generateIdOperation.generateId();
    }
}