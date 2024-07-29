package com.omgservers.schema.model.message;

import com.omgservers.schema.model.message.body.ClientOutgoingMessageBodyModel;
import com.omgservers.schema.model.message.body.ConnectionUpgradeMessageBodyModel;
import com.omgservers.schema.model.message.body.DisconnectionReasonMessageBodyModel;
import com.omgservers.schema.model.message.body.MatchmakerAssignmentMessageBodyModel;
import com.omgservers.schema.model.message.body.RuntimeAssignmentMessageBodyModel;
import com.omgservers.schema.model.message.body.ServerOutgoingMessageBodyModel;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyModel;

public enum MessageQualifierEnum {
    // Client messages
    CLIENT_OUTGOING_MESSAGE(ClientOutgoingMessageBodyModel.class),
    // Server messages
    SERVER_WELCOME_MESSAGE(ServerWelcomeMessageBodyModel.class),
    RUNTIME_ASSIGNMENT_MESSAGE(RuntimeAssignmentMessageBodyModel.class),
    MATCHMAKER_ASSIGNMENT_MESSAGE(MatchmakerAssignmentMessageBodyModel.class),
    CONNECTION_UPGRADE_MESSAGE(ConnectionUpgradeMessageBodyModel.class),
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
