// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.graphql.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.preparsed.PreparsedDocumentProvider;
import graphql.parser.ParserOptions;
import graphql.parser.ParserOptions.Builder;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import java.util.function.Consumer;
import org.hiero.mirror.graphql.scalar.GraphQlDuration;
import org.hiero.mirror.graphql.scalar.GraphQlTimestamp;
import org.springframework.boot.graphql.autoconfigure.GraphQlSourceBuilderCustomizer;
import org.springframework.boot.jackson2.autoconfigure.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
class GraphQlConfiguration {

    static {
        // Configure GraphQL parsing limits to reject malicious input
        Consumer<Builder> consumer = b -> b.maxCharacters(10000).maxRuleDepth(100).maxTokens(1000).maxWhitespaceTokens(1000);
        ParserOptions.setDefaultParserOptions(ParserOptions.getDefaultParserOptions().transform(consumer));
        ParserOptions.setDefaultOperationParserOptions(ParserOptions.getDefaultOperationParserOptions().transform(consumer));
    }

    @Bean
    GraphQlSourceBuilderCustomizer graphQlCustomizer(PreparsedDocumentProvider provider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Bean
    SchemaDirectiveWiring validationDirectives() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Configure JSON parsing limits to reject malicious input
    @Bean
    @SuppressWarnings("removal")
    Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
