package com.semanticRelationsExtractor.main;

import com.semanticRelationsExtractor.data.SemanticExtractionData;

import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public interface SemanticExtractionProcessor {

    SemanticExtractionData process(List<String> tokensList, List<String> tagsList) throws InterruptedException;

}
