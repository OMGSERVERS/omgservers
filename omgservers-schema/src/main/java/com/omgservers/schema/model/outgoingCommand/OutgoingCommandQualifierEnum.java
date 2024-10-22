package com.omgservers.schema.model.outgoingCommand;

import com.omgservers.schema.model.outgoingCommand.body.BroadcastMessageOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.KickClientOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.MulticastMessageOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.RequestMatchmakingOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.RespondClientOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.SetProfileOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.StopMatchmakingOutgoingCommandBodyDto;
import com.omgservers.schema.model.outgoingCommand.body.UpgradeConnectionOutgoingCommandBodyDto;
import lombok.Getter;

@Getter
public enum OutgoingCommandQualifierEnum {

    RESPOND_CLIENT(RespondClientOutgoingCommandBodyDto.class, false),
    SET_PROFILE(SetProfileOutgoingCommandBodyDto.class, true),

    MULTICAST_MESSAGE(MulticastMessageOutgoingCommandBodyDto.class, false),
    BROADCAST_MESSAGE(BroadcastMessageOutgoingCommandBodyDto.class, false),

    REQUEST_MATCHMAKING(RequestMatchmakingOutgoingCommandBodyDto.class, true),
    KICK_CLIENT(KickClientOutgoingCommandBodyDto.class, true),
    STOP_MATCHMAKING(StopMatchmakingOutgoingCommandBodyDto.class, true),

    UPGRADE_CONNECTION(UpgradeConnectionOutgoingCommandBodyDto.class, true);

    final Class<? extends OutgoingCommandBodyDto> bodyClass;
    final Boolean infoLogging;

    OutgoingCommandQualifierEnum(Class<? extends OutgoingCommandBodyDto> bodyClass, Boolean infoLogging) {
        this.bodyClass = bodyClass;
        this.infoLogging = infoLogging;
    }
}
