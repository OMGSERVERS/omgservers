package com.omgservers.model.message;

import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.message.body.ClientMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;

public enum MessageQualifierEnum {
    // Client messages
    CLIENT_MESSAGE(ClientMessageBodyModel.class),
    MATCHMAKER_MESSAGE(MatchmakerMessageBodyModel.class),
    // Server messages
    WELCOME_MESSAGE(WelcomeMessageBodyModel.class),
    ASSIGNMENT_MESSAGE(AssignmentMessageBodyModel.class),
    SERVER_MESSAGE(ServerMessageBodyModel.class);

    final Class<? extends MessageBodyModel> bodyClass;

    MessageQualifierEnum(Class<? extends MessageBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MessageBodyModel> getBodyClass() {
        return bodyClass;
    }
}
