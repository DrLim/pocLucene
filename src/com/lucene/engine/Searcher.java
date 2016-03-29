package com.lucene.engine;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;

public interface Searcher {

	Document getDocument(int docId) throws IOException;

	TopDocs performSearch(String queryString, int n) throws IOException, ParseException;

}
