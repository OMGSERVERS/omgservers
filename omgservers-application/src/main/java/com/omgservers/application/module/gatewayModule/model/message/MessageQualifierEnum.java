package com.omgservers.application.module.gatewayModule.model.message;

import com.omgservers.application.module.gatewayModule.model.message.body.*;

public enum MessageQualifierEnum {
    SIGN_IN_MESSAGE(SignInMessageBodyModel.class),
    SIGN_UP_MESSAGE(SignUpMessageBodyModel.class),
    MATCHMAKER_MESSAGE(MatchmakerMessageBodyModel.class),
    CREDENTIALS_MESSAGE(CredentialsMessageBodyModel.class),
    EVENT_MESSAGE(EventMessageBodyModel.class),
    CHANGE_MESSAGE(ChangeMessageBodyModel.class),
    ERROR_MESSAGE(ErrorMessageBodyModel.class);

    final Class<? extends MessageBodyModel> bodyClass;

    MessageQualifierEnum(Class<? extends MessageBodyModel> bodyClass) {
        this.bodyClass = bodyClass;
    }

    public Class<? extends MessageBodyModel> getBodyClass() {
        return bodyClass;
    }
}
