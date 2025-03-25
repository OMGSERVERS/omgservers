package com.omgservers.service.service.task.impl.method.executeDeploymentTask.component;

import lombok.Getter;

import java.util.*;

abstract class DeploymentAssigner<T, A, R> {

    final Map<Long, Resource> indexById;
    final TreeSet<Resource> sortedSet;
    final int maxAssignments;

    public DeploymentAssigner(int maxAssignments) {
        this.maxAssignments = maxAssignments;
        this.sortedSet = new TreeSet<>(Comparator.comparing(Resource::getSize)
                .thenComparingLong(Resource::getId));
        this.indexById = new HashMap<>();
    }

    public void addResource(final Long resourceId, T resource) {
        final var container = new Resource(resourceId, resource);
        sortedSet.add(container);
        indexById.put(resourceId, container);
    }

    public void addAssignment(final Long resourceId, A assignment) {
        final var resource = indexById.get(resourceId);
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

        void addAssignment(A assignment) {
            assignments.add(assignment);
            size += 1;
        }

        boolean addRequest(R request) {
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
