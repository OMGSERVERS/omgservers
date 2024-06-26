package com.omgservers.model.outgoingCommand;

import com.omgservers.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.KickClientOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.RequestMatchmakingOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.RespondClientOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.SetAttributesOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.SetProfileOutgoingCommandBodyModel;
import com.omgservers.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyModel;
import lombok.Getter;

@Getter
public enum OutgoingCommandQualifierEnum {

    RESPOND_CLIENT(RespondClientOutgoingCommandBodyModel.class, false),
    SET_ATTRIBUTES(SetAttributesOutgoingCommandBodyModel.class, true),
    SET_PROFILE(SetProfileOutgoingCommandBodyModel.class, true),

    MULTICAST_MESSAGE(MulticastMessageOutgoingCommandBodyModel.class, false),
    BROADCAST_MESSAGE(BroadcastMessageOutgoingCommandBodyModel.class, false),

    REQUEST_MATCHMAKING(RequestMatchmakingOutgoingCommandBodyModel.class, true),
    KICK_CLIENT(KickClientOutgoingCommandBodyModel.class, true),
    STOP_MATCHMAKING(StopMatchmakingOutgoingCommandBodyModel.class, true);

    final Class<? extends OutgoingCommandBodyModel> bodyClass;
    final Boolean infoLogging;

    OutgoingCommandQualifierEnum(Class<? extends OutgoingCommandBodyModel> bodyClass, Boolean infoLogging) {
        this.bodyClass = bodyClass;
        this.infoLogging = infoLogging;
    }
}
