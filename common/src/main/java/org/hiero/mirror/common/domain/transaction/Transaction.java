// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.domain.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hiero.mirror.common.converter.EntityIdConverter;
import org.hiero.mirror.common.converter.ListToStringSerializer;
import org.hiero.mirror.common.converter.ObjectToStringSerializer;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.token.NftTransfer;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Persistable;

// For builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@Entity
@NoArgsConstructor
public class Transaction implements Persistable<Long> {

    @ToString.Exclude
    private byte[] batchKey;

    private Long chargedTxFee;

    private Long congestionPricingMultiplier;

    @Id
    private Long consensusTimestamp;

    @Convert(converter = EntityIdConverter.class)
    private EntityId entityId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ErrataType errata;

    private Boolean highVolume;

    private Long highVolumePricingMultiplier;

    private Integer index;

    // Repeated sequence of payer_account_id, valid_start_ns
    @JsonSerialize(using = ListToStringSerializer.class)
    private List<Long> innerTransactions;

    private Long initialBalance;

    @JsonSerialize(using = ObjectToStringSerializer.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ItemizedTransfer> itemizedTransfer;

    @ToString.Exclude
    private byte[][] maxCustomFees;

    private Long maxFee;

    @ToString.Exclude
    private byte[] memo;

    @JsonSerialize(using = ObjectToStringSerializer.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<NftTransfer> nftTransfer;

    @Convert(converter = EntityIdConverter.class)
    private EntityId nodeAccountId;

    private Integer nonce;

    private Long parentConsensusTimestamp;

    @Convert(converter = EntityIdConverter.class)
    private EntityId payerAccountId;

    private Integer result;

    private boolean scheduled;

    @ToString.Exclude
    private byte[] transactionBytes;

    @ToString.Exclude
    private byte[] transactionHash;

    @ToString.Exclude
    private byte[] transactionRecordBytes;

    private Integer type;

    private Long validDurationSeconds;

    private Long validStartNs;

    public void addItemizedTransfer(@NonNull ItemizedTransfer itemizedTransfer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addNftTransfer(@NonNull NftTransfer nftTransfer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

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

    public void addInnerTransaction(Transaction transaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TransactionHash toTransactionHash() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
