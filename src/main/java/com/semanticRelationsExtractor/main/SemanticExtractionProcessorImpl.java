package com.semanticRelationsExtractor.main;

//import com.postagger.main.PosTagger;
//import com.postagger.main.PosTaggerImpl;

import com.semanticRelationsExtractor.data.SemanticExtractionData;
import com.semanticRelationsExtractor.data.SemanticPreprocessingData;
import com.semanticRelationsExtractor.data.TrainingDataRow;
import com.semanticRelationsExtractor.extraction.SemanticRelationsExtractor;
import com.semanticRelationsExtractor.preprocessing.SemanticPreprocessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
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
    public List<SemanticExtractionData> process(List<TrainingDataRow> trainingDataRowList) throws InterruptedException {
        List<SemanticExtractionData> semanticExtractionDataList = new ArrayList<>();
        for (TrainingDataRow trainingDataRow : trainingDataRowList) {
            if (trainingDataRow.containsSubSentences()) {
                for (int i = 0; i <= trainingDataRow.getTokensMultiList().size() - 1; i++) {
                    List<String> tokensList = trainingDataRow.getTokensMultiList().get(i);
                    List<String> tagsList = trainingDataRow.getTagsMultiList().get(i);
                    SemanticExtractionData semanticExtractionData = processSentence(tokensList, tagsList);
                    if (semanticExtractionData != null) {
                        semanticExtractionDataList.add(semanticExtractionData);
                    }
                }
            } else {
                List<String> tagsList = trainingDataRow.getTagsList();
                List<String> tokensList = trainingDataRow.getTokensList();
                processSentence(tokensList, tagsList);
                SemanticExtractionData semanticExtractionData = processSentence(tokensList, tagsList);
                if (semanticExtractionData != null) {
                    semanticExtractionDataList.add(semanticExtractionData);
                }
            }
        }
        return semanticExtractionDataList;
    }


    private SemanticExtractionData processSentence(List<String> tokensList, List<String> tagsList) {
        SemanticPreprocessingData semanticPreprocessingData = semanticPreprocessor.preprocess(tokensList, tagsList);
        if (semanticPreprocessingData.canGoToExtraction()) {
            return semanticRelationsExtractor.extract(semanticPreprocessingData);
        }
        return null;
    }
}
