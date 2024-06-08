package com.omgservers.model.dto.root.rootEntityRef;

import com.omgservers.model.rootEntityRef.RootEntityRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRootEntityRefResponse {

    RootEntityRefModel rootEntityRef;
}
