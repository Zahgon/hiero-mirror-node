// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain;

import java.util.List;

public interface StreamFile<T extends StreamItem> {

    default StreamFile<T> clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    StreamFile<T> copy();

    byte[] getBytes();

    void setBytes(byte[] bytes);

    Long getConsensusStart();

    void setConsensusStart(Long timestamp);

    Long getConsensusEnd();

    default void setConsensusEnd(Long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Long getCount();

    String getFileHash();

    // Get the chained hash of the stream file
    default String getHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void setHash(String hash) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default Long getIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void setIndex(Long index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    List<T> getItems();

    void setItems(List<T> items);

    Long getLoadEnd();

    Long getLoadStart();

    default String getMetadataHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getName();

    void setName(String name);

    // Get the chained hash of the previous stream file
    default String getPreviousHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    default void setPreviousHash(String previousHash) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    StreamType getType();
}
