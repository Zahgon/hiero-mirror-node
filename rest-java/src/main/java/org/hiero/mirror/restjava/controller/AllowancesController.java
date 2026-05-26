// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.restjava.controller;

import static org.hiero.mirror.restjava.common.Constants.ACCOUNT_ID;
import static org.hiero.mirror.restjava.common.Constants.APPLICATION_JSON;
import static org.hiero.mirror.restjava.common.Constants.DEFAULT_LIMIT;
import static org.hiero.mirror.restjava.common.Constants.MAX_LIMIT;
import static org.hiero.mirror.restjava.common.Constants.TOKEN_ID;
import static org.hiero.mirror.restjava.jooq.domain.Tables.NFT_ALLOWANCE;
import com.google.common.collect.ImmutableSortedMap;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Map;
import java.util.function.Function;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.rest.model.NftAllowance;
import org.hiero.mirror.rest.model.NftAllowancesResponse;
import org.hiero.mirror.restjava.common.LinkFactory;
import org.hiero.mirror.restjava.dto.NftAllowanceRequest;
import org.hiero.mirror.restjava.mapper.NftAllowanceMapper;
import org.hiero.mirror.restjava.parameter.EntityIdParameter;
import org.hiero.mirror.restjava.parameter.EntityIdRangeParameter;
import org.hiero.mirror.restjava.service.Bound;
import org.hiero.mirror.restjava.service.NftAllowanceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CustomLog
@RequestMapping(value = "/api/v1/accounts/{id}/allowances", produces = APPLICATION_JSON)
@RequiredArgsConstructor
@RestController
public class AllowancesController {

    private static final Map<Boolean, Function<NftAllowance, Map<String, String>>> EXTRACTORS = Map.of(true, nftAllowance -> ImmutableSortedMap.of(ACCOUNT_ID, nftAllowance.getSpender(), TOKEN_ID, nftAllowance.getTokenId()), false, nftAllowance -> ImmutableSortedMap.of(ACCOUNT_ID, nftAllowance.getOwner(), TOKEN_ID, nftAllowance.getTokenId()));

    private final LinkFactory linkFactory;

    private final NftAllowanceService service;

    private final NftAllowanceMapper nftAllowanceMapper;

    @GetMapping(value = "/nfts")
    NftAllowancesResponse getNftAllowances(@PathVariable EntityIdParameter id, @RequestParam(name = ACCOUNT_ID, required = false) @Size(max = 2) EntityIdRangeParameter[] accountIds, @RequestParam(defaultValue = DEFAULT_LIMIT) @Positive @Max(MAX_LIMIT) int limit, @RequestParam(defaultValue = "asc") Sort.Direction order, @RequestParam(defaultValue = "true") boolean owner, @RequestParam(name = TOKEN_ID, required = false) @Size(max = 2) EntityIdRangeParameter[] tokenIds) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
