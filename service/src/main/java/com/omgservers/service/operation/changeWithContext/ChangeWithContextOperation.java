package com.omgservers.service.operation.changeWithContext;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.function.BiFunction;

public interface ChangeWithContextOperation {

    <T> Uni<ChangeContext<T>> changeWithContext(BiFunction<ChangeContext<T>, SqlConnection, Uni<T>> changeFunction);
}
