package com.omgservers.schema.module.root.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRootEntityRefsResponse {

    List<RootEntityRefModel> rootEntityRefs;
}
