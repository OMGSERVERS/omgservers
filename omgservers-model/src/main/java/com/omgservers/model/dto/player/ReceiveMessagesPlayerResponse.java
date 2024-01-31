package com.omgservers.model.dto.player;

import com.omgservers.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessagesPlayerResponse {

    List<MessageModel> messages;
}
