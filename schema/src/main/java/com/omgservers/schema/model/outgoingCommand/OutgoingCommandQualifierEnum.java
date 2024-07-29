package com.omgservers.schema.model.outgoingCommand;

import com.omgservers.schema.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.KickClientOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.RequestMatchmakingOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.RespondClientOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.SetAttributesOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.SetProfileOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.UpgradeConnectionOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.KickClientOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.RequestMatchmakingOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.RespondClientOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.SetAttributesOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.SetProfileOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyModel;
import com.omgservers.schema.model.outgoingCommand.body.UpgradeConnectionOutgoingCommandBodyModel;
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
    STOP_MATCHMAKING(StopMatchmakingOutgoingCommandBodyModel.class, true),

    UPGRADE_CONNECTION(UpgradeConnectionOutgoingCommandBodyModel.class, true);

    final Class<? extends OutgoingCommandBodyModel> bodyClass;
    final Boolean infoLogging;

    OutgoingCommandQualifierEnum(Class<? extends OutgoingCommandBodyModel> bodyClass, Boolean infoLogging) {
        this.bodyClass = bodyClass;
        this.infoLogging = infoLogging;
    }
}
