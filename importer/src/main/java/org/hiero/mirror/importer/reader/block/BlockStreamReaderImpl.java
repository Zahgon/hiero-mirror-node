// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.reader.block;

import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.BLOCK_FOOTER;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.BLOCK_HEADER;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.BLOCK_PROOF;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.EVENT_HEADER;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.RECORD_FILE;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.ROUND_HEADER;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.SIGNED_TRANSACTION;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.STATE_CHANGES;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.TRACE_DATA;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.TRANSACTION_OUTPUT;
import static com.hedera.hapi.block.stream.protoc.BlockItem.ItemCase.TRANSACTION_RESULT;
import static org.hiero.mirror.common.util.DomainUtils.bytesToHex;
import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hapi.block.stream.output.protoc.StateChanges;
import com.hedera.hapi.block.stream.output.protoc.TransactionOutput;
import com.hedera.hapi.block.stream.output.protoc.TransactionOutput.TransactionCase;
import com.hedera.hapi.block.stream.protoc.BlockItem;
import com.hedera.hapi.block.stream.trace.protoc.TraceData;
import com.hederahashgraph.api.proto.java.AtomicBatchTransactionBody;
import com.hederahashgraph.api.proto.java.BlockHashAlgorithm;
import com.hederahashgraph.api.proto.java.SignedTransaction;
import com.hederahashgraph.api.proto.java.TransactionBody;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.apache.commons.codec.binary.Hex;
import org.hiero.mirror.common.domain.DigestAlgorithm;
import org.hiero.mirror.common.domain.transaction.BlockFile;
import org.hiero.mirror.common.domain.transaction.BlockTransaction;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.hiero.mirror.importer.reader.block.hash.BlockRootHashDigest;
import org.hiero.mirror.importer.reader.block.record.RecordFileItemReader;
import org.jspecify.annotations.Nullable;

@CustomLog
@Named
@RequiredArgsConstructor
public final class BlockStreamReaderImpl implements BlockStreamReader {

    private final RecordFileItemReader recordFileItemReader;

    @Override
    public BlockFile read(final BlockStream blockStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void readBlockFooter(final ReaderContext context) {
        final var blockItem = context.readBlockItemFor(BLOCK_FOOTER);
        if (blockItem == null) {
            throw new InvalidStreamFileException("Missing block footer in block " + context.getFilename());
        }
        final var blockFooter = blockItem.getBlockFooter();
        final byte[] previousHash = toBytes(blockFooter.getPreviousBlockRootHash());
        context.getBlockFile().previousHash(bytesToHex(previousHash)).rawPreviousHash(previousHash);
    }

    private void readBlockHeader(final ReaderContext context) {
        final var blockItem = context.readBlockItemFor(BLOCK_HEADER);
        if (blockItem == null) {
            throw new InvalidStreamFileException("Missing block header in block " + context.getFilename());
        }
        final var blockFileBuilder = context.getBlockFile();
        final var blockHeader = blockItem.getBlockHeader();
        if (blockHeader.getHashAlgorithm().equals(BlockHashAlgorithm.SHA2_384)) {
            blockFileBuilder.digestAlgorithm(DigestAlgorithm.SHA_384);
        } else {
            throw new InvalidStreamFileException(String.format("Unsupported hash algorithm %s in block header of block %s", blockHeader.getHashAlgorithm(), context.getFilename()));
        }
        blockFileBuilder.blockHeader(blockHeader);
        blockFileBuilder.index(blockHeader.getNumber());
    }

    private void readBlockProof(final ReaderContext context) {
        final var blockItem = context.readBlockItemFor(BLOCK_PROOF);
        if (blockItem == null) {
            throw new InvalidStreamFileException("Missing block proof in block " + context.getFilename());
        }
        final var blockProof = blockItem.getBlockProof();
        context.getBlockFile().blockProof(blockProof);
        // Read remaining blockProof block items. In a later release, implement support of multiple blockProof items,
        // primarily for wrapped record files which come with both SignedRecordFileProof and StateProof
        while (context.readBlockItemFor(BLOCK_PROOF) != null) {
            log.debug("Skip remaining block proof block items");
        }
    }

    private void readEvents(final ReaderContext context) {
        boolean advanced;
        do {
            final int lastIndex = context.getIndex();
            context.readBlockItemFor(EVENT_HEADER);
            readSignedTransactions(context);
            advanced = context.getIndex() != lastIndex;
        } while (advanced);
    }

    private void readSignedTransactions(final ReaderContext context) {
        BlockItem protoBlockItem;
        SignedTransactionInfo signedTransactionInfo;
        try {
            while ((signedTransactionInfo = context.getSignedTransaction()) != null) {
                final var signedTransaction = SignedTransaction.parseFrom(signedTransactionInfo.signedTransaction());
                final var transactionBody = TransactionBody.parseFrom(signedTransaction.getBodyBytes());
                final var transactionResultProtoBlockItem = context.readBlockItemFor(TRANSACTION_RESULT);
                if (transactionResultProtoBlockItem == null) {
                    if (signedTransactionInfo.userTransactionInBatch()) {
                        // #12313 - when a user transaction in an atomic batch fails, any subsequent user transaction
                        // in the same batch will not execute thus won't have a TransactionResult block item
                        context.resetBatchTransaction();
                    }
                    // System transactions won't have transactionResult either, continue to next block item
                    continue;
                }
                final var transactionOutputs = new EnumMap<TransactionCase, TransactionOutput>(TransactionCase.class);
                while ((protoBlockItem = context.readBlockItemFor(TRANSACTION_OUTPUT)) != null) {
                    final var transactionOutput = protoBlockItem.getTransactionOutput();
                    transactionOutputs.put(transactionOutput.getTransactionCase(), transactionOutput);
                }
                final var traceDataList = new ArrayList<TraceData>();
                while ((protoBlockItem = context.readBlockItemFor(TRACE_DATA)) != null) {
                    traceDataList.add(protoBlockItem.getTraceData());
                }
                final var stateChangesList = new ArrayList<StateChanges>();
                final var transactionResult = transactionResultProtoBlockItem.getTransactionResult();
                while ((protoBlockItem = context.readBlockItemFor(STATE_CHANGES)) != null) {
                    final var stateChanges = protoBlockItem.getStateChanges();
                    if (!Objects.equals(transactionResult.getConsensusTimestamp(), stateChanges.getConsensusTimestamp())) {
                        break;
                    }
                    stateChangesList.add(stateChanges);
                }
                final var blockTransaction = BlockTransaction.builder().previous(context.getLastBlockTransaction()).signedTransaction(signedTransaction).signedTransactionBytes(signedTransactionInfo.signedTransaction()).stateChanges(Collections.unmodifiableList(stateChangesList)).traceData(Collections.unmodifiableList(traceDataList)).transactionBody(transactionBody).transactionResult(transactionResult).transactionOutputs(Collections.unmodifiableMap(transactionOutputs)).build();
                context.setLastBlockTransaction(blockTransaction, signedTransactionInfo.userTransactionInBatch());
                final var blockFileBuilder = context.getBlockFile();
                blockFileBuilder.item(blockTransaction);
                if (blockTransaction.getTransactionBody().hasLedgerIdPublication() && blockTransaction.isSuccessful()) {
                    blockFileBuilder.lastLedgerIdPublicationTransaction(blockTransaction);
                }
            }
        } catch (InvalidProtocolBufferException e) {
            throw new InvalidStreamFileException("Failed to deserialize Transaction from block " + context.getFilename(), e);
        }
    }

    private void readRounds(ReaderContext context) {
        BlockItem blockItem;
        while ((blockItem = context.readBlockItemFor(ROUND_HEADER)) != null) {
            context.getBlockFile().onNewRound(blockItem.getRoundHeader().getRoundNumber());
            readEvents(context);
        }
    }

    @Value
    private static class ReaderContext {

        private BlockFile.BlockFileBuilder blockFile;

        private List<BlockItem> blockItems;

        private BlockRootHashDigest blockRootHashDigest;

        private String filename;

        @NonFinal
        private int batchIndex;

        @NonFinal
        @Nullable
        private AtomicBatchTransactionBody batchBody;

        @NonFinal
        private int index;

        @NonFinal
        @Nullable
        private BlockTransaction lastBlockTransaction;

        @NonFinal
        @Nullable
        private BlockTransaction lastUserTransactionInBatch;

        @NonFinal
        @Nullable
        private BlockTransaction lastChildTransaction;

        ReaderContext(final List<BlockItem> blockItems, final String filename) {
            this.blockFile = BlockFile.builder();
            this.blockItems = blockItems;
            this.blockRootHashDigest = new BlockRootHashDigest();
            this.filename = filename;
        }

        @Nullable
        SignedTransactionInfo getSignedTransaction() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the current block item if it matches the itemCase, and advances the index. Index can also advance
         * until consecutive non-transaction statechanges block items are read
         *
         * @param itemCase - block item case
         * @return The matching block item, or null
         */
        @Nullable
        BlockItem readBlockItemFor(final BlockItem.ItemCase itemCase) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void resetBatchTransaction() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void setLastBlockTransaction(final BlockTransaction lastBlockTransaction, final boolean userTransactionInBatch) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private static boolean shouldSkip(final BlockItem.ItemCase actual, final BlockItem.ItemCase expected) {
            // Skip a statechanges block item when the expected type is not statechanges / trace data / transaction
            // output. Such a statechanges block item must be a non-transaction statechanges block item. When
            // the expected type is either trace data or transaction output, the statechanges block item should not be
            // skipped since it may belong to the current signed transaction
            return actual != expected && actual == STATE_CHANGES && expected != TRACE_DATA && expected != TRANSACTION_OUTPUT;
        }

        private void consumeBlockItem(final BlockItem blockItem) {
            blockRootHashDigest.addBlockItem(blockItem);
            index++;
        }
    }

    private record SignedTransactionInfo(byte[] signedTransaction, boolean userTransactionInBatch) {
    }
}
