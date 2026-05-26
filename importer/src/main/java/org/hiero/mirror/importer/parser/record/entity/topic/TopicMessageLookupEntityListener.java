// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.entity.topic;

import jakarta.inject.Named;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.domain.topic.TopicMessage;
import org.hiero.mirror.common.domain.topic.TopicMessageLookup;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.db.TimePartitionService;
import org.hiero.mirror.importer.exception.ImporterException;
import org.hiero.mirror.importer.parser.record.RecordParserProperties;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.hiero.mirror.importer.parser.record.entity.ParserContext;
import org.springframework.core.annotation.Order;

@Named
@Order(4)
@RequiredArgsConstructor
public class TopicMessageLookupEntityListener implements EntityListener {

    private static final long FILE_CLOSE_INTERVAL_SECS = StreamType.RECORD.getFileCloseInterval().toSeconds();

    private static final String TOPIC_MESSAGE_TABLE_NAME = "topic_message";

    private final EntityProperties entityProperties;

    private final ParserContext parserContext;

    private final TimePartitionService timePartitionService;

    private final RecordParserProperties parserProperties;

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onTopicMessage(TopicMessage topicMessage) throws ImporterException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TopicMessageLookup mergeTopicMessageLookup(TopicMessageLookup cached, TopicMessageLookup newValue) {
        cached.setSequenceNumberRange(cached.getSequenceNumberRange().span(newValue.getSequenceNumberRange()));
        cached.setTimestampRange(cached.getTimestampRange().span(newValue.getTimestampRange()));
        return cached;
    }
}
