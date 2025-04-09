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
    // Service -> Client
    CLIENT_GREETED(ClientGreetedMessageBodyDto.class),
    RUNTIME_ASSIGNED(RuntimeAssignedMessageBodyDto.class),
    CONNECTION_UPGRADED(ConnectionUpgradedMessageBodyDto.class),
    CLIENT_DELETED(ClientDeletedMessageBodyDto.class),
    MESSAGE_PRODUCED(MessageProducedMessageBodyDto.class),
    // Runtime -> Service
    RESPOND_CLIENT(RespondClientMessageBodyDto.class),
    SET_PROFILE(SetProfileMessageBodyDto.class),
    MULTICAST_MESSAGE(MulticastMessageBodyDto.class),
    BROADCAST_MESSAGE(BroadcastMessageBodyDto.class),
    REQUEST_MATCHMAKING(RequestMatchmakingMessageBodyDto.class),
    KICK_CLIENT(KickClientMessageBodyDto.class),
    STOP_MATCHMAKING(StopMatchmakingMessageBodyDto.class),
    UPGRADE_CONNECTION(UpgradeConnectionMessageBodyDto.class),
    // Service -> Runtime
    RUNTIME_CREATED(RuntimeCreatedMessageBodyDto.class),
    CLIENT_ASSIGNED(ClientAssignedMessageBodyDto.class),
    CLIENT_REMOVED(ClientRemovedMessageBodyDto.class),
    MESSAGE_RECEIVED(MessageReceivedMessageBodyDto.class);

    final Class<? extends MessageBodyDto> bodyClass;
}
