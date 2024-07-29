package com.omgservers.schema.entrypoint.player;

import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.MessageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangePlayerRequest {

    @NotNull
    Long clientId;

    @NotNull
    List<MessageModel> outgoingMessages;

    @NotNull
    List<Long> consumedMessages;
}
