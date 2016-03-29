package com.lucene.engine.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.lucene.engine.ConstantsLucene;
import com.lucene.engine.Searcher;

public class SearcherImpl implements Searcher{

	private IndexSearcher searcher = null;
    private MultiFieldQueryParser parser = null;
    Path dir=Paths.get(ConstantsLucene.INDEX_DIR);
    /** Creates a new instance of SearchEngine */
    public SearcherImpl() throws IOException {
        searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(dir)));
        parser = new MultiFieldQueryParser(ConstantsLucene.ALL_FIELDS, new StandardAnalyzer());
        parser.setAllowLeadingWildcard(Boolean.TRUE);
    }
    
    @Override
    public TopDocs performSearch(String queryString, int n)
    throws IOException, ParseException{
        Query query = parser.parse(queryString);        
        return searcher.search(query, n);
    }
    
    @Override
    public Document getDocument(int docId)
    throws IOException {
        return searcher.doc(docId);
    }
}
