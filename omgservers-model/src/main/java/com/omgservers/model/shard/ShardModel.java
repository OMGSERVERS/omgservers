package com.omgservers.model.shard;

import java.net.URI;

public record ShardModel(int shard, URI serverUri, boolean foreign, boolean locked) {
}
