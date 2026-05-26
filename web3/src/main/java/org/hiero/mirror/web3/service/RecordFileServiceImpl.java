// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.transaction.RecordFile;
import org.hiero.mirror.web3.repository.RecordFileRepository;
import org.hiero.mirror.web3.viewmodel.BlockType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordFileServiceImpl implements RecordFileService {

    private final RecordFileRepository recordFileRepository;

    @Override
    public Optional<RecordFile> findByBlockType(BlockType block) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Optional<RecordFile> findByTimestamp(Long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
