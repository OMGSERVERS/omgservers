package com.omgservers.schema.module.client;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
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
