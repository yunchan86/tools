package com.utuky.lucene.commons.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;

import com.utuky.lucene.commons.define.IndexWriterConfigTypeConstant;

public class FacIndexWriterConfig {

	public static IndexWriterConfig getInstance(String analyzerType) {
		IndexWriterConfig iwc = null ;
		Analyzer analyzer = null ;
		if(IndexWriterConfigTypeConstant.ANALYZER_STANDARD.equals(analyzerType)) {
			analyzer = new StandardAnalyzer() ;
		}else {
			analyzer = new StandardAnalyzer() ;
		}
		iwc = new IndexWriterConfig(analyzer) ;
		return iwc ;
	}
}
