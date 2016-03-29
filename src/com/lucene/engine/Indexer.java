package com.lucene.engine;

import java.io.IOException;

import org.apache.lucene.index.IndexWriter;

import com.lucene.pojo.Hotel;

public interface Indexer {
	

	void closeIndexWriter() throws IOException;

	IndexWriter getIndexWriter(boolean create) throws Throwable;

	void indexHotel(Hotel hotel) throws Throwable;

	void rebuildIndexes() throws Throwable;

}
