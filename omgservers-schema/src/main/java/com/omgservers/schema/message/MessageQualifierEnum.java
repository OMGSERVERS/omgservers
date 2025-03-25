package com.omgservers.schema.message;

import com.omgservers.schema.message.body.BroadcastMessageBodyDto;
import com.omgservers.schema.message.body.ClientAssignedMessageBodyDto;
import com.omgservers.schema.message.body.ClientDeletedMessageBodyDto;
import com.omgservers.schema.message.body.ClientGreetedMessageBodyDto;
import com.omgservers.schema.message.body.ClientRemovedMessageBodyDto;
import com.omgservers.schema.message.body.ConnectionUpgradedMessageBodyDto;
import com.omgservers.schema.message.body.KickClientMessageBodyDto;
import com.omgservers.schema.message.body.MessageProducedMessageBodyDto;
import com.omgservers.schema.message.body.MessageReceivedMessageBodyDto;
import com.omgservers.schema.message.body.MulticastMessageBodyDto;
import com.omgservers.schema.message.body.RequestMatchmakingMessageBodyDto;
import com.omgservers.schema.message.body.RespondClientMessageBodyDto;
import com.omgservers.schema.message.body.RuntimeAssignedMessageBodyDto;
import com.omgservers.schema.message.body.RuntimeCreatedMessageBodyDto;
import com.omgservers.schema.message.body.SetProfileMessageBodyDto;
import com.omgservers.schema.message.body.StopMatchmakingMessageBodyDto;
import com.omgservers.schema.message.body.UpgradeConnectionMessageBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageQualifierEnum {
    CLIENT_GREETED(ClientGreetedMessageBodyDto.class, true, false),
    RUNTIME_ASSIGNED(RuntimeAssignedMessageBodyDto.class, true, false),
    CONNECTION_UPGRADED(ConnectionUpgradedMessageBodyDto.class, true, false),
    CLIENT_DELETED(ClientDeletedMessageBodyDto.class, true, false),
    MESSAGE_PRODUCED(MessageProducedMessageBodyDto.class, true, false),

    RESPOND_CLIENT(RespondClientMessageBodyDto.class, false, true),
    SET_PROFILE(SetProfileMessageBodyDto.class, false, true),
    MULTICAST_MESSAGE(MulticastMessageBodyDto.class, false, true),
    BROADCAST_MESSAGE(BroadcastMessageBodyDto.class, false, true),
    REQUEST_MATCHMAKING(RequestMatchmakingMessageBodyDto.class, false, true),
    KICK_CLIENT(KickClientMessageBodyDto.class, false, true),
    STOP_MATCHMAKING(StopMatchmakingMessageBodyDto.class, false, true),
    UPGRADE_CONNECTION(UpgradeConnectionMessageBodyDto.class, false, true),

    RUNTIME_CREATED(RuntimeCreatedMessageBodyDto.class, false, true),
    CLIENT_ASSIGNED(ClientAssignedMessageBodyDto.class, false, true),
    CLIENT_REMOVED(ClientRemovedMessageBodyDto.class, false, true),
    MESSAGE_RECEIVED(MessageReceivedMessageBodyDto.class, false, true);

    final Class<? extends MessageBodyDto> bodyClass;

    final boolean client;
    final boolean runtime;
}
