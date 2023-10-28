package com.omgservers.model.message;

import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.message.body.ChangeMessageBodyModel;
import com.omgservers.model.message.body.CredentialsMessageBodyModel;
import com.omgservers.model.message.body.MatchMessageBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.message.body.RevocationMessageBodyModel;
import com.omgservers.model.message.body.ServerMessageBodyModel;
import com.omgservers.model.message.body.SignInMessageBodyModel;
import com.omgservers.model.message.body.SignUpMessageBodyModel;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;

public enum MessageQualifierEnum {
    // Incoming messages
    SIGN_IN_MESSAGE(SignInMessageBodyModel.class),
    SIGN_UP_MESSAGE(SignUpMessageBodyModel.class),
    MATCHMAKER_MESSAGE(MatchmakerMessageBodyModel.class),
    MATCH_MESSAGE(MatchMessageBodyModel.class),
    CHANGE_MESSAGE(ChangeMessageBodyModel.class),
    // Outgoing messages
    CREDENTIALS_MESSAGE(CredentialsMessageBodyModel.class),
    WELCOME_MESSAGE(WelcomeMessageBodyModel.class),
    ASSIGNMENT_MESSAGE(AssignmentMessageBodyModel.class),
    REVOCATION_MESSAGE(RevocationMessageBodyModel.class),
    SERVER_MESSAGE(ServerMessageBodyModel.class);

    final Class<? extends MessageBodyModel> bodyClass;

    MessageQualifierEnum(Class<? extends MessageBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MessageBodyModel> getBodyClass() {
        return bodyClass;
    }
}
