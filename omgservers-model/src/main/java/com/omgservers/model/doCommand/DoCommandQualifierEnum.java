package com.omgservers.model.doCommand;

import com.omgservers.model.doCommand.body.DoBroadcastMessageCommandBodyModel;
import com.omgservers.model.doCommand.body.DoKickClientCommandBodyModel;
import com.omgservers.model.doCommand.body.DoMulticastMessageCommandBodyModel;
import com.omgservers.model.doCommand.body.DoRespondClientCommandBodyModel;
import com.omgservers.model.doCommand.body.DoSetAttributesCommandBodyModel;
import com.omgservers.model.doCommand.body.DoSetProfileCommandBodyModel;
import com.omgservers.model.doCommand.body.DoStopMatchmakingCommandBodyModel;

public enum DoCommandQualifierEnum {

    DO_RESPOND_CLIENT(DoRespondClientCommandBodyModel.class, false),
    DO_SET_ATTRIBUTES(DoSetAttributesCommandBodyModel.class, true),
    DO_SET_PROFILE(DoSetProfileCommandBodyModel.class, true),

    DO_MULTICAST_MESSAGE(DoMulticastMessageCommandBodyModel.class, false),
    DO_BROADCAST_MESSAGE(DoBroadcastMessageCommandBodyModel.class, false),

    DO_KICK_CLIENT(DoKickClientCommandBodyModel.class, true),
    DO_STOP_MATCHMAKING(DoStopMatchmakingCommandBodyModel.class, true);

    final Class<? extends DoCommandBodyModel> bodyClass;
    final Boolean infoLogging;

    DoCommandQualifierEnum(Class<? extends DoCommandBodyModel> bodyClass, Boolean infoLogging) {
        this.bodyClass = bodyClass;
        this.infoLogging = infoLogging;
    }

    public Class<? extends DoCommandBodyModel> getBodyClass() {
        return bodyClass;
    }

    public Boolean getInfoLogging() {
        return infoLogging;
    }
}
