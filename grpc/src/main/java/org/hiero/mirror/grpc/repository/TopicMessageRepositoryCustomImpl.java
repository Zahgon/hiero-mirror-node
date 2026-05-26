// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.stream.Stream;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.HibernateHints;
import org.hiero.mirror.common.domain.topic.TopicMessage;
import org.hiero.mirror.grpc.domain.TopicMessageFilter;

@CustomLog
@Named
@RequiredArgsConstructor
public class TopicMessageRepositoryCustomImpl implements TopicMessageRepositoryCustom {

    private static final String CONSENSUS_TIMESTAMP = "consensusTimestamp";

    private static final String TOPIC_ID = "topicId";

    // make the cost estimation of using the index on (topic_id, consensus_timestamp) lower than that of
    // the primary key so pg planner will choose the better index when querying topic messages by id
    private static final String TOPIC_MESSAGES_BY_ID_QUERY_HINT = "set local random_page_cost = 0";

    private final EntityManager entityManager;

    @Override
    public Stream<TopicMessage> findByFilter(TopicMessageFilter filter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
