// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.downloader;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.importer.domain.StreamFileSignature;
import org.hiero.mirror.importer.domain.StreamFilename;
import org.hiero.mirror.importer.exception.SignatureVerificationException;

@CustomLog
@Named
@RequiredArgsConstructor
public class ConsensusValidatorImpl implements ConsensusValidator {

    private final CommonDownloaderProperties commonDownloaderProperties;

    /**
     * Validates that the signature files satisfy the consensus requirement:
     * <ol>
     *  <li>If NodeStakes are within the NodeStakeRepository, at least 1/3 of the total node stake amount has been
     *  signature verified.</li>
     *  <li>If no NodeStakes are in the NodeStakeRepository, At least 1/3 signature files are present</li>
     * </ol>
     *
     * @param signatures a list of signature files which have the same filename
     * @throws SignatureVerificationException
     */
    @Override
    public void validate(Collection<StreamFileSignature> signatures) throws SignatureVerificationException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean canReachConsensus(long stake, BigDecimal stakeRequiredForConsensus) {
        return BigDecimal.valueOf(stake).compareTo(stakeRequiredForConsensus) >= 0;
    }

    private BigDecimal getStakeRequiredForConsensus(long totalStake) {
        if (totalStake == 0) {
            throw new SignatureVerificationException("Invalid total staking weight. Consensus not " + "reached");
        }
        return BigDecimal.valueOf(totalStake).multiply(commonDownloaderProperties.getConsensusRatio()).setScale(0, RoundingMode.CEILING);
    }
}
