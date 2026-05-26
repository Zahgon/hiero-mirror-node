// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.addressbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

// For builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Data
@Entity
@NoArgsConstructor
public class NetworkStake implements Persistable<Long> {

    @jakarta.persistence.Id
    private long consensusTimestamp;

    private long epochDay;

    private long maxStakeRewarded;

    private long maxStakingRewardRatePerHbar;

    private long maxTotalReward;

    private long nodeRewardFeeDenominator;

    private long nodeRewardFeeNumerator;

    private long reservedStakingRewards;

    private long rewardBalanceThreshold;

    private long stakeTotal;

    private long stakingPeriod;

    private long stakingPeriodDuration;

    private long stakingPeriodsStored;

    private long stakingRewardFeeDenominator;

    private long stakingRewardFeeNumerator;

    private long stakingRewardRate;

    private long stakingStartThreshold;

    private long unreservedStakingRewardBalance;

    @JsonIgnore
    @Override
    public Long getId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
