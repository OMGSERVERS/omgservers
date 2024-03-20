package com.omgservers.model.event.body.player;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchmakerMessageReceivedEventBodyModel extends EventBodyModel {

    @NotNull
    Long clientId;

    @NotNull
    MessageModel message;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MESSAGE_RECEIVED;
    }
}
