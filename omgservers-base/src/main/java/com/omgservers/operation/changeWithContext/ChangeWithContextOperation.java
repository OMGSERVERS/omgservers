package com.omgservers.operation.changeWithContext;

import com.omgservers.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.function.BiFunction;

public interface ChangeWithContextOperation {

    <T> Uni<T> changeWithContext(BiFunction<ChangeContext, SqlConnection, Uni<T>> changeFunction);
}
