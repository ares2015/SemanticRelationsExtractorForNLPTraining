package com.semanticRelationsExtractor.main;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.TrainingDataRow;

import java.util.List;

/**
 * Created by Oliver on 5/15/2017.
 */
public interface SemanticExtractionProcessor {

    List<SemanticExtractionData> process(List<TrainingDataRow> trainingDataRowList) throws InterruptedException;

}
