package com.omgservers.model.message;

import com.omgservers.model.message.body.ClientOutgoingMessageBodyModel;
import com.omgservers.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerAssignmentMessageBodyModel;
import com.omgservers.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.model.message.body.ServerWelcomeMessageBodyModel;

public enum MessageQualifierEnum {
    // Client messages
    CLIENT_OUTGOING_MESSAGE(ClientOutgoingMessageBodyModel.class),
    // Server messages
    SERVER_WELCOME_MESSAGE(ServerWelcomeMessageBodyModel.class),
    RUNTIME_ASSIGNMENT_MESSAGE(RuntimeAssignmentMessageBodyModel.class),
    MATCHMAKER_ASSIGNMENT_MESSAGE(MatchmakerAssignmentMessageBodyModel.class),
    DISCONNECTION_REASON_MESSAGE(DisconnectionReasonMessageBodyModel.class),
    SERVER_OUTGOING_MESSAGE(ServerOutgoingMessageBodyModel.class);

    final Class<? extends MessageBodyModel> bodyClass;

    MessageQualifierEnum(Class<? extends MessageBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MessageBodyModel> getBodyClass() {
        return bodyClass;
    }
}
