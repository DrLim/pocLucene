package com.lucene.engine.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.lucene.dao.HotelDAO;
import com.lucene.engine.ConstantsLucene;
import com.lucene.engine.Indexer;
import com.lucene.pojo.Hotel;

public class IndexerImpl implements Indexer {

	private static Logger LOGGER = Logger.getLogger(IndexerImpl.class);
	
	private IndexWriter indexWriter = null;
	Path dir = Paths.get(ConstantsLucene.INDEX_DIR);

	@Override
	public IndexWriter getIndexWriter(boolean create) throws Throwable {
		if (indexWriter == null) {
			Directory indexDir = FSDirectory.open(dir);
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			config.setOpenMode(OpenMode.CREATE);
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	@Override
	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	@Override
	public void indexHotel(Hotel hotel) throws Throwable {
		LOGGER.info("Indexing hotel:" + hotel);
		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();
		doc.add(new StringField(ConstantsLucene.FIELD_ID, hotel.getId(), Field.Store.YES));
		doc.add(new TextField(ConstantsLucene.FIELD_NAME, hotel.getName(), Field.Store.YES));
		doc.add(new TextField(ConstantsLucene.FIELD_CITY, hotel.getCity(), Field.Store.YES));
		doc.add(new TextField(ConstantsLucene.FIELD_DESCRIPTION, hotel.getDescription(), Field.Store.YES));
		writer.addDocument(doc);

	}

	@Override
	public void rebuildIndexes() throws Throwable {
		//
		// Erase existing index
		//
		getIndexWriter(true);
		//
		// Index all Accommodation entries
		//
		Hotel[] hotels = HotelDAO.getHotels();
		for (Hotel hotel : hotels) {
			indexHotel(hotel);
		}
		indexWriter.commit();
		
		//
		// Don't forget to close the index writer when done
		//
		closeIndexWriter();
	}

}
