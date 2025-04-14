package com.omgservers.schema.shard.client.clientMessage;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientMessagesResponse {

    List<ClientMessageModel> clientMessages;
}
