// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader.record;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimaps;
import com.hedera.services.stream.proto.SidecarType;
import com.hedera.services.stream.proto.TransactionSidecarRecord;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.inject.Named;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import org.hiero.mirror.common.domain.StreamType;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.domain.transaction.SidecarFile;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.addressbook.ConsensusNodeService;
import org.hiero.mirror.importer.config.DateRangeCalculator;
import org.hiero.mirror.importer.domain.StreamFileData;
import org.hiero.mirror.importer.domain.StreamFilename;
import org.hiero.mirror.importer.downloader.Downloader;
import org.hiero.mirror.importer.downloader.NodeSignatureVerifier;
import org.hiero.mirror.importer.downloader.StreamFileNotifier;
import org.hiero.mirror.importer.downloader.block.cutover.CutoverService;
import org.hiero.mirror.importer.downloader.provider.StreamFileProvider;
import org.hiero.mirror.importer.exception.HashMismatchException;
import org.hiero.mirror.importer.parser.record.sidecar.SidecarProperties;
import org.hiero.mirror.importer.reader.record.ProtoRecordFileReader;
import org.hiero.mirror.importer.reader.record.RecordFileReader;
import org.hiero.mirror.importer.reader.record.sidecar.SidecarFileReader;
import org.hiero.mirror.importer.reader.signature.SignatureFileReader;
import org.hiero.mirror.importer.util.Utility;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Named
public class RecordFileDownloader extends Downloader<RecordFile, RecordItem> {

    private static final String HASH_TYPE_SIDECAR = "Sidecar";

    private final CutoverService cutoverService;

    private final SidecarFileReader sidecarFileReader;

    private final SidecarProperties sidecarProperties;

    @SuppressWarnings("java:S107")
    public RecordFileDownloader(ConsensusNodeService consensusNodeService, CutoverService cutoverService, RecordDownloaderProperties downloaderProperties, ImporterProperties importerProperties, MeterRegistry meterRegistry, DateRangeCalculator dateRangeCalculator, NodeSignatureVerifier nodeSignatureVerifier, SidecarFileReader sidecarFileReader, SidecarProperties sidecarProperties, SignatureFileReader signatureFileReader, StreamFileNotifier streamFileNotifier, StreamFileProvider streamFileProvider, RecordFileReader streamFileReader) {
        super(consensusNodeService, downloaderProperties, importerProperties, meterRegistry, dateRangeCalculator, nodeSignatureVerifier, signatureFileReader, streamFileNotifier, streamFileProvider, streamFileReader);
        this.cutoverService = cutoverService;
        this.sidecarFileReader = sidecarFileReader;
        this.sidecarProperties = sidecarProperties;
    }

    @Override
    @Scheduled(fixedDelayString = "#{@recordDownloaderProperties.getFrequency().toMillis()}")
    public void download() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onVerified(StreamFileData streamFileData, RecordFile recordFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean shouldDownload() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void setStreamFileIndex(RecordFile recordFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void downloadSidecars(StreamFilename recordFilename, RecordFile recordFile) {
        // do nothing if both writing files and parsing sidecars options are disabled, or sidecars are empty
        if (!downloaderProperties.isWriteFiles() && !sidecarProperties.isEnabled() || recordFile.getSidecars().isEmpty()) {
            return;
        }
        var acceptedTypes = sidecarProperties.getTypeOrdinals();
        var records = Flux.fromIterable(recordFile.getSidecars()).filter(sidecar -> acceptedTypes.isEmpty() || sidecar.getTypes().stream().anyMatch(acceptedTypes::contains)).flatMap(sidecar -> getSidecar(recordFilename, sidecar)).flatMapIterable(SidecarFile::getRecords).filter(t -> acceptedTypes.isEmpty() || acceptedTypes.contains(getSidecarType(t))).collect(Multimaps.toMultimap(TransactionSidecarRecord::getConsensusTimestamp, Function.identity(), ArrayListMultimap::create)).block();
        if (records == null) {
            return;
        }
        recordFile.getItems().forEach(recordItem -> {
            var timestamp = recordItem.getTransactionRecord().getConsensusTimestamp();
            if (records.containsKey(timestamp)) {
                recordItem.setSidecarRecords(records.get(timestamp));
            }
        });
    }

    private Mono<SidecarFile> getSidecar(StreamFilename recordFilename, SidecarFile sidecar) {
        var sidecarFilename = StreamFilename.from(recordFilename, sidecar.getName());
        return streamFileProvider.get(sidecarFilename).map(streamFileData -> {
            sidecarFileReader.read(sidecar, streamFileData);
            if (!Arrays.equals(sidecar.getHash(), sidecar.getActualHash())) {
                throw new HashMismatchException(sidecar.getName(), sidecar.getHash(), sidecar.getActualHash(), HASH_TYPE_SIDECAR);
            }
            if (downloaderProperties.isWriteFiles()) {
                var streamPath = importerProperties.getArchiveDestinationFolderPath(streamFileData);
                Utility.archiveFile(streamFileData.getFilePath(), sidecar.getBytes(), streamPath);
            }
            if (!sidecarProperties.isPersistBytes()) {
                sidecar.setBytes(null);
            }
            return sidecar;
        });
    }

    private int getSidecarType(TransactionSidecarRecord transactionSidecarRecord) {
        return switch(transactionSidecarRecord.getSidecarRecordsCase()) {
            case ACTIONS ->
                SidecarType.CONTRACT_ACTION_VALUE;
            case BYTECODE ->
                SidecarType.CONTRACT_BYTECODE_VALUE;
            case STATE_CHANGES ->
                SidecarType.CONTRACT_STATE_CHANGE_VALUE;
            default ->
                {
                    Utility.handleRecoverableError("Unknown sidecar transaction record type at {}: {}", transactionSidecarRecord.getConsensusTimestamp(), transactionSidecarRecord.getSidecarRecordsCase());
                    yield SidecarType.SIDECAR_TYPE_UNKNOWN_VALUE;
                }
        };
    }
}
