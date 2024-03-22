package com.omgservers.model.dto.client;

import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewClientMatchmakerRefsResponse {

    List<ClientMatchmakerRefModel> clientMatchmakerRefs;
}
