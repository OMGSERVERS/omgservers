package com.omgservers.application.model;

import java.net.URI;

public record ShardModel(int shard, URI serverUri, boolean foreign, boolean locked) {
}
