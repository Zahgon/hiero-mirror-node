// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.batch;

import jakarta.inject.Named;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import lombok.CustomLog;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.hiero.mirror.common.domain.transaction.TransactionHash;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RequiredArgsConstructor
@CustomLog
@Named
public class TransactionHashTxManager implements TransactionSynchronization {

    private final Map<String, ThreadState> threadConnections = new ConcurrentHashMap<>();

    private final DataSource dataSource;

    private long itemCount;

    private long recordTimestamp;

    private String tableName;

    @Override
    public void afterCompletion(int status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void initialize(Collection<?> items, String tableName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Start new transaction or update state of existing transaction
     *
     * @return state of the thread
     */
    public ThreadState updateAndGetThreadState(int shard) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SneakyThrows
    private ThreadState setupThreadTransaction() {
        // Clean thread from previous run
        TransactionSynchronizationManager.clear();
        TransactionSynchronizationManager.unbindResourceIfPossible(dataSource);
        // initialize transaction for thread
        TransactionSynchronizationManager.initSynchronization();
        TransactionSynchronizationManager.setActualTransactionActive(true);
        // Subsequent calls to get connection on this thread will use the same connection
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false);
        return new ThreadState(connection);
    }

    Map<String, ThreadState> getThreadConnections() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    long getItemCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Data
    @ToString(exclude = "connection")
    static class ThreadState {

        private final Connection connection;

        private final Set<Integer> processedShards = new HashSet<>();

        private int status = -1;

        public ThreadState(Connection connection) {
            this.connection = connection;
        }
    }
}
