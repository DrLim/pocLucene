package com.lucene.main;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.lucene.engine.ConstantsLucene;
import com.lucene.engine.Indexer;
import com.lucene.engine.Searcher;
import com.lucene.engine.impl.IndexerImpl;
import com.lucene.engine.impl.SearcherImpl;

public class Main {

	private static Logger LOGGER = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		LOGGER.info("start indexing");
		Indexer indexer = new IndexerImpl();
		try {
			indexer.rebuildIndexes();
		} catch (Throwable e) {
			LOGGER.error("Error");
			LOGGER.error(e);
		}
		LOGGER.info("End indexing");
		LOGGER.info("Start searching");
		String term = "* AND -Paris";
		LOGGER.info("Searching value : "+term);
		try {
			Searcher searcher = new SearcherImpl();
			TopDocs docs = searcher.performSearch(term, 100);
			LOGGER.info("Results found: " + docs.totalHits);
	        ScoreDoc[] hits = docs.scoreDocs;
	        for (int i = 0; i < hits.length; i++) {
	            Document doc = searcher.getDocument(hits[i].doc);
	            System.out.println(doc.get(ConstantsLucene.FIELD_ID)
	            				   + " " + doc.get(ConstantsLucene.FIELD_NAME)
	                               + " " + doc.get(ConstantsLucene.FIELD_CITY)
	                               + " " + doc.get(ConstantsLucene.FIELD_DESCRIPTION)
	                               + " (" + hits[i].score + ")");

	        }
	        LOGGER.info("performSearch done");
			
		} catch (IOException | ParseException e) {
			LOGGER.error(e);
		} 
	}

}
