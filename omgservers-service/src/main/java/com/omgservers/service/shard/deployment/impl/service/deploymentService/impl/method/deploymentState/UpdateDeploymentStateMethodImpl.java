package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentState;

import com.omgservers.schema.model.deploymentChangeOfState.DeploymentLobbyResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentChangeOfState.DeploymentMatchmakerResourceToUpdateStatusDto;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateRequest;
import com.omgservers.schema.module.deployment.deploymentState.UpdateDeploymentStateResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand.DeleteDeploymentCommandOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.DeleteDeploymentLobbyAssignmentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment.UpsertDeploymentLobbyAssignmentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.DeleteDeploymentLobbyResourceOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.UpdateDeploymentLobbyResourceStatusOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.UpsertDeploymentLobbyResourceOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment.UpsertDeploymentMatchmakerAssignmentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.DeleteDeploymentMatchmakerResourceOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.UpsertDeploymentMatchmakerResourceOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.DeleteDeploymentRequestOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateDeploymentStateMethodImpl implements UpdateDeploymentStateMethod {

    final UpdateDeploymentMatchmakerResourceStatusOperation updateDeploymentMatchmakerResourceStatusOperation;
    final UpsertDeploymentMatchmakerAssignmentOperation upsertDeploymentMatchmakerAssignmentOperation;
    final DeleteDeploymentMatchmakerAssignmentOperation deleteDeploymentMatchmakerAssignmentOperation;
    final UpdateDeploymentLobbyResourceStatusOperation updateDeploymentLobbyResourceStatusOperation;
    final UpsertDeploymentMatchmakerResourceOperation upsertDeploymentMatchmakerResourceOperation;
    final DeleteDeploymentMatchmakerResourceOperation deleteDeploymentMatchmakerResourceOperation;
    final UpsertDeploymentLobbyAssignmentOperation upsertDeploymentLobbyAssignmentOperation;
    final DeleteDeploymentLobbyAssignmentOperation deleteDeploymentLobbyAssignmentOperation;
    final UpsertDeploymentLobbyResourceOperation upsertDeploymentLobbyResourceOperation;
    final DeleteDeploymentLobbyResourceOperation deleteDeploymentLobbyResourceOperation;
    final DeleteDeploymentCommandOperation deleteDeploymentCommandOperation;
    final DeleteDeploymentRequestOperation deleteDeploymentRequestOperation;

    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateDeploymentStateResponse> execute(final UpdateDeploymentStateRequest request) {
        log.trace("{}", request);

        final var deploymentChangeOfState = request.getDeploymentChangeOfState();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();

                    final var deploymentId = request.getDeploymentId();

                    return changeWithContextOperation.<Void>changeWithContext((changeContext, sqlConnection) ->
                            deleteDeploymentCommands(changeContext,
                                    sqlConnection,
                                    shard,
                                    deploymentId,
                                    deploymentChangeOfState.getDeploymentCommandsToDelete())
                                    .flatMap(voidItem -> deleteDeploymentRequests(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentRequestsToDelete()))
                                    .flatMap(voidItem -> upsertDeploymentLobbyResources(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentChangeOfState.getDeploymentLobbyResourcesToSync()))
                                    .flatMap(voidItem -> updateDeploymentLobbyResourcesStatus(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentLobbyResourcesToUpdateStatus()))
                                    .flatMap(voidItem -> deleteDeploymentLobbyResources(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentLobbyResourcesToDelete()))
                                    .flatMap(voidItem -> upsertDeploymentLobbyAssignment(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentChangeOfState.getDeploymentLobbyAssignmentToSync()))
                                    .flatMap(voidItem -> deleteDeploymentLobbyAssignments(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentLobbyAssignmentToDelete()))
                                    .flatMap(voidItem -> upsertDeploymentMatchmakerResources(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentChangeOfState.getDeploymentMatchmakerResourcesToSync()))
                                    .flatMap(voidItem -> updateDeploymentMatchmakerResourcesStatus(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentMatchmakerResourcesToUpdateStatus()))
                                    .flatMap(voidItem -> deleteDeploymentMatchmakerResources(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentMatchmakerResourcesToDelete()))
                                    .flatMap(voidItem -> upsertDeploymentMatchmakerAssignment(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentChangeOfState.getDeploymentMatchmakerAssignmentToSync()))
                                    .flatMap(voidItem -> deleteDeploymentMatchmakerAssignments(changeContext,
                                            sqlConnection,
                                            shard,
                                            deploymentId,
                                            deploymentChangeOfState.getDeploymentMatchmakerAssignmentToDelete()))
                    );
                })
                .replaceWith(Boolean.TRUE)
                .map(UpdateDeploymentStateResponse::new);
    }

    Uni<Void> deleteDeploymentCommands(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long deploymentId,
                                       final List<Long> deploymentCommands) {
        return Multi.createFrom().iterable(deploymentCommands)
                .onItem().transformToUniAndConcatenate(deploymentCommandId ->
                        deleteDeploymentCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentCommandId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteDeploymentRequests(final ChangeContext<?> changeContext,
                                       final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long deploymentId,
                                       final List<Long> deploymentRequests) {
        return Multi.createFrom().iterable(deploymentRequests)
                .onItem().transformToUniAndConcatenate(deploymentRequestId ->
                        deleteDeploymentRequestOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentRequestId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertDeploymentLobbyResources(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final List<DeploymentLobbyResourceModel> deploymentLobbyResources) {
        return Multi.createFrom().iterable(deploymentLobbyResources)
                .onItem().transformToUniAndConcatenate(deploymentLobbyResource ->
                        upsertDeploymentLobbyResourceOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentLobbyResource))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateDeploymentLobbyResourcesStatus(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final Long deploymentId,
                                                   final List<DeploymentLobbyResourceToUpdateStatusDto> deploymentLobbyResources) {
        return Multi.createFrom().iterable(deploymentLobbyResources)
                .onItem().transformToUniAndConcatenate(deploymentLobbyResource ->
                        updateDeploymentLobbyResourceStatusOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentLobbyResource.id(),
                                deploymentLobbyResource.status())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteDeploymentLobbyResources(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final Long deploymentId,
                                             final List<Long> deploymentLobbyResources) {
        return Multi.createFrom().iterable(deploymentLobbyResources)
                .onItem().transformToUniAndConcatenate(deploymentLobbyResourceId ->
                        deleteDeploymentLobbyResourceOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentLobbyResourceId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertDeploymentLobbyAssignment(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final List<DeploymentLobbyAssignmentModel> deploymentLobbyAssignments) {
        return Multi.createFrom().iterable(deploymentLobbyAssignments)
                .onItem().transformToUniAndConcatenate(deploymentLobbyAssignment ->
                        upsertDeploymentLobbyAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentLobbyAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteDeploymentLobbyAssignments(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long deploymentId,
                                               final List<Long> deploymentLobbyAssignments) {
        return Multi.createFrom().iterable(deploymentLobbyAssignments)
                .onItem().transformToUniAndConcatenate(deploymentLobbyAssignmentId ->
                        deleteDeploymentLobbyAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentLobbyAssignmentId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertDeploymentMatchmakerResources(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final List<DeploymentMatchmakerResourceModel> deploymentMatchmakerResources) {
        return Multi.createFrom().iterable(deploymentMatchmakerResources)
                .onItem().transformToUniAndConcatenate(deploymentMatchmakerResource ->
                        upsertDeploymentMatchmakerResourceOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentMatchmakerResource))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> updateDeploymentMatchmakerResourcesStatus(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final Long deploymentId,
                                                        final List<DeploymentMatchmakerResourceToUpdateStatusDto> deploymentMatchmakerResources) {
        return Multi.createFrom().iterable(deploymentMatchmakerResources)
                .onItem().transformToUniAndConcatenate(deploymentMatchmakerResource ->
                        updateDeploymentMatchmakerResourceStatusOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentMatchmakerResource.id(),
                                deploymentMatchmakerResource.status())
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteDeploymentMatchmakerResources(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long deploymentId,
                                                  final List<Long> deploymentMatchmakerResources) {
        return Multi.createFrom().iterable(deploymentMatchmakerResources)
                .onItem().transformToUniAndConcatenate(deploymentMatchmakerResourceId ->
                        deleteDeploymentMatchmakerResourceOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentMatchmakerResourceId))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> upsertDeploymentMatchmakerAssignment(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final List<DeploymentMatchmakerAssignmentModel> deploymentMatchmakerAssignments) {
        return Multi.createFrom().iterable(deploymentMatchmakerAssignments)
                .onItem().transformToUniAndConcatenate(deploymentMatchmakerAssignment ->
                        upsertDeploymentMatchmakerAssignmentOperation.execute(changeContext,
                                sqlConnection,
                                shard,
                                deploymentMatchmakerAssignment))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Void> deleteDeploymentMatchmakerAssignments(final ChangeContext<?> changeContext,
                                                    final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final Long deploymentId,
                                                    final List<Long> deploymentMatchmakerAssignments) {
        return Multi.createFrom().iterable(deploymentMatchmakerAssignments)
                .onItem().transformToUniAndConcatenate(deploymentMatchmakerAssignmentId ->
                        deleteDeploymentMatchmakerAssignmentOperation.execute(
                                changeContext,
                                sqlConnection,
                                shard,
                                deploymentId,
                                deploymentMatchmakerAssignmentId))
                .collect().asList()
                .replaceWithVoid();
    }
}
