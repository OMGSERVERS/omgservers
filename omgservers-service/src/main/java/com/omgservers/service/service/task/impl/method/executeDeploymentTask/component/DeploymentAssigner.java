package com.omgservers.service.service.task.impl.method.executeDeploymentTask.component;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

abstract class DeploymentAssigner<T, A, R> {

    final Map<Long, Resource> resourceById;
    final TreeSet<Resource> sortedSet;
    final int maxAssignments;

    public DeploymentAssigner(final int maxAssignments) {
        this.maxAssignments = maxAssignments;
        this.sortedSet = new TreeSet<>(Comparator.comparing(Resource::getSize)
                .thenComparingLong(Resource::getId));
        this.resourceById = new HashMap<>();
    }

    public void addResource(final Long resourceId, final T resource) {
        final var container = new Resource(resourceId, resource);
        sortedSet.add(container);
        resourceById.put(resourceId, container);
    }

    public void addAssignment(final Long resourceId, final A assignment) {
        final var resource = resourceById.get(resourceId);
        if (Objects.nonNull(resource)) {
            resource.addAssignment(assignment);
        }
    }

    public Optional<T> assign(final R request) {
        for (final var container : sortedSet) {
            if (container.addRequest(request)) {
                return Optional.of(container.getResource());
            }
        }

        return Optional.empty();
    }

    class Resource {

        @Getter
        Long id;

        @Getter
        final T resource;

        final List<A> assignments;

        final List<R> requests;

        @Getter
        int size;

        public Resource(final Long id, final T resource) {
            this.id = id;
            this.resource = resource;

            assignments = new ArrayList<>();
            requests = new ArrayList<>();
            size = 0;
        }

        void addAssignment(final A assignment) {
            assignments.add(assignment);
            size += 1;
        }

        boolean addRequest(final R request) {
            if (size < maxAssignments) {
                requests.add(request);
                size += 1;
                return true;
            } else {
                return false;
            }
        }
    }
}
