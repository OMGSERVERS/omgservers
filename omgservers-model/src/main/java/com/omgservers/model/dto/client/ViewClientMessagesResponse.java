package com.omgservers.model.dto.client;

import com.omgservers.model.clientMessage.ClientMessageModel;
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
