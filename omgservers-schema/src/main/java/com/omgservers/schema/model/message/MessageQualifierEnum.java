package com.omgservers.schema.model.message;

import com.omgservers.schema.model.message.body.ClientOutgoingMessageBodyDto;
import com.omgservers.schema.model.message.body.ConnectionUpgradeMessageBodyDto;
import com.omgservers.schema.model.message.body.DisconnectionReasonMessageBodyDto;
import com.omgservers.schema.model.message.body.MatchmakerAssignmentMessageBodyDto;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyDto;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyDto;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyDto;

public enum MessageQualifierEnum {
    // Client messages
    CLIENT_OUTGOING_MESSAGE(ClientOutgoingMessageBodyDto.class),
    // Server messages
    SERVER_WELCOME_MESSAGE(ServerWelcomeMessageBodyDto.class),
    RUNTIME_ASSIGNMENT_MESSAGE(RuntimeAssignmentMessageBodyDto.class),
    MATCHMAKER_ASSIGNMENT_MESSAGE(MatchmakerAssignmentMessageBodyDto.class),
    CONNECTION_UPGRADE_MESSAGE(ConnectionUpgradeMessageBodyDto.class),
    DISCONNECTION_REASON_MESSAGE(DisconnectionReasonMessageBodyDto.class),
    SERVER_OUTGOING_MESSAGE(ServerOutgoingMessageBodyDto.class);

    final Class<? extends MessageBodyDto> bodyClass;

    MessageQualifierEnum(Class<? extends MessageBodyDto> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MessageBodyDto> getBodyClass() {
        return bodyClass;
    }
}
