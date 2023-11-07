package com.omgservers.model.doCommand;

import com.omgservers.model.doCommand.body.DoBroadcastCommandBodyModel;
import com.omgservers.model.doCommand.body.DoChangeCommandBodyModel;
import com.omgservers.model.doCommand.body.DoKickCommandBodyModel;
import com.omgservers.model.doCommand.body.DoMulticastCommandBodyModel;
import com.omgservers.model.doCommand.body.DoRespondCommandBodyModel;
import com.omgservers.model.doCommand.body.DoSetAttributesCommandBodyModel;
import com.omgservers.model.doCommand.body.DoSetProfileCommandBodyModel;
import com.omgservers.model.doCommand.body.DoStopCommandBodyModel;
import com.omgservers.model.doCommand.body.DoUnicastCommandBodyModel;

public enum DoCommandQualifierEnum {

    DO_RESPOND(DoRespondCommandBodyModel.class, false),
    DO_SET_ATTRIBUTES(DoSetAttributesCommandBodyModel.class, true),
    DO_SET_PROFILE(DoSetProfileCommandBodyModel.class, true),

    DO_UNICAST(DoUnicastCommandBodyModel.class, false),
    DO_MULTICAST(DoMulticastCommandBodyModel.class, false),
    DO_BROADCAST(DoBroadcastCommandBodyModel.class, false),

    DO_CHANGE(DoChangeCommandBodyModel.class, false),

    DO_KICK(DoKickCommandBodyModel.class, true),
    DO_STOP(DoStopCommandBodyModel.class, true);

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
