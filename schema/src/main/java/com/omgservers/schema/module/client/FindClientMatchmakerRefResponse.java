package com.omgservers.schema.module.client;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindClientMatchmakerRefResponse {

    ClientMatchmakerRefModel clientMatchmakerRef;
}
