package com.omgservers.schema.module.alias;

import com.omgservers.schema.model.alias.AliasModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewAliasesResponse {

    List<AliasModel> aliases;
}
