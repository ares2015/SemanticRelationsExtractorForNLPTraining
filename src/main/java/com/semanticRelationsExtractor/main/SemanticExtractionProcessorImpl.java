package com.semanticRelationsExtractor.main;


import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


/**
 * Created by Oliver on 5/15/2017.
 */
public class SemanticExtractionProcessorImpl implements SemanticExtractionProcessor {

    private SemanticPreprocessor semanticPreprocessor;

    private SemanticRelationsExtractor semanticRelationsExtractor;

    public SemanticExtractionProcessorImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_beans.xml");
        this.semanticPreprocessor = (SemanticPreprocessor) context.getBean("semanticPreprocessor");
        this.semanticRelationsExtractor = (SemanticRelationsExtractor) context.getBean("semanticRelationsExtractor");
    }

    @Override
    public SemanticExtractionData process(List<String> tokensList, List<String> tagsList) throws InterruptedException {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            return semanticRelationsExtractor.extract(semanticPreprocessingData);
        }
        return new SemanticExtractionData();
    }
}