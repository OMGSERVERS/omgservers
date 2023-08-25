package com.omgservers.dto.userModule;

import com.omgservers.model.client.ClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClientInternalResponse {

    ClientModel client;
}
