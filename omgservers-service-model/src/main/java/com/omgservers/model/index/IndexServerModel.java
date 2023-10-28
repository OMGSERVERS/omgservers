package com.omgservers.model.index;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexServerModel {

    static public IndexServerModel create(URI uri) {
        return create(uri, new ArrayList<>());
    }

    static public IndexServerModel create(URI uri, List<Integer> shards) {
        IndexServerModel indexServerModel = new IndexServerModel();
        indexServerModel.setUri(uri);
        indexServerModel.setShards(shards);
        return indexServerModel;
    }

    @NotNull
    URI uri;

    @NotNull
    @Size(max = 32767)
    List<Integer> shards;
}
