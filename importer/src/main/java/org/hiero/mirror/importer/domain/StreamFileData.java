// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.domain;

import com.google.common.base.Suppliers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Supplier;
import lombok.CustomLog;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hiero.mirror.importer.exception.FileOperationException;
import org.hiero.mirror.importer.exception.InvalidStreamFileException;
import org.jspecify.annotations.NullMarked;

@CustomLog
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NullMarked
@Value
public class StreamFileData {

    private static final CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory(true);

    @EqualsAndHashCode.Include
    private final StreamFilename streamFilename;

    private final Supplier<byte[]> bytes;

    @Getter(lazy = true)
    private final byte[] decompressedBytes = decompressBytes();

    private final Instant lastModified;

    private static StreamFileData readStreamFileData(File file, StreamFilename streamFilename) {
        if (!file.exists() || !file.canRead() || !file.isFile()) {
            throw new FileOperationException("Unable to read file " + file);
        }
        Supplier<byte[]> bytes = Suppliers.memoize(() -> {
            try {
                return FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new FileOperationException("Unable to read file to byte array", e);
            }
        });
        var lastModified = Instant.ofEpochMilli(file.lastModified());
        return new StreamFileData(streamFilename, bytes, lastModified);
    }

    public static StreamFileData from(File file) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static StreamFileData from(File file, StreamFilename streamFilename) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static StreamFileData from(final Path basePath, final StreamFilename streamFilename) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Used for testing String based files like CSVs
    public static StreamFileData from(String filename, String contents) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Used for testing with raw bytes
    public static StreamFileData from(String filename, byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] getBytes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public InputStream getInputStream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getFilename() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getFilePath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private byte[] decompressBytes() {
        var compressor = streamFilename.getCompressor();
        if (StringUtils.isBlank(compressor)) {
            return getBytes();
        }
        try (var inputStream = new ByteArrayInputStream(getBytes());
            var compressorInputStream = compressorStreamFactory.createCompressorInputStream(compressor, inputStream)) {
            return compressorInputStream.readAllBytes();
        } catch (IOException e) {
            var filename = streamFilename.getFilename();
            log.error("Failed to decompress stream file {}", filename);
            throw new InvalidStreamFileException(filename, e);
        }
    }
}
