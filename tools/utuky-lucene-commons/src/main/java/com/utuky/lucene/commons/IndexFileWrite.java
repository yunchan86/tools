package com.utuky.lucene.commons;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.utuky.commons.tools.utils.CollectionUtils;
import com.utuky.commons.tools.utils.StringUtils;

public class IndexFileWrite {

	public static void addIndex(String indexpath,IndexWriterConfig iwc,List<IndexableField> indexRowData) {
		IndexWriter indexWriter = null;  
		try {
			if(StringUtils.isBlank(indexpath)) return ;
			if(CollectionUtils.isBlank(indexRowData)) return ;
			Directory directory = getDirectory(indexpath); 
			indexWriter = new IndexWriter(directory, iwc);
			Document doc = createIndexDocument(indexRowData) ;
			indexWriter.addDocument(doc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {  
	            if (indexWriter != null) {  
	                indexWriter.close();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		}
	}
	
	public static void addIndexList(String indexpath,IndexWriterConfig iwc,List<List<IndexableField>> rowList) {
		IndexWriter indexWriter = null;  
		try {
			if(StringUtils.isBlank(indexpath)) return ;
			if(CollectionUtils.isBlank(rowList)) return ;
			Directory directory = getDirectory(indexpath); 
			indexWriter = new IndexWriter(directory, iwc);
			List<Document> docs = createIndexDocumentList(rowList) ;
			indexWriter.addDocuments(docs);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {  
	            if (indexWriter != null) {  
	                indexWriter.close();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		}
	}
	
	protected static Directory getDirectory(String indexpath) {
		Path path = Paths.get(indexpath) ;
		return  getDirectory(path) ;
	}
	protected static Directory getDirectory(Path path) {
		Directory directory=null;
		try {
			directory = FSDirectory.open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return directory ;
	}
	
	protected static Document createIndexDocument(List<IndexableField> indexRowData) {
		Document doc = new Document();
		for(IndexableField field:indexRowData) {
			doc.add(field);
		}
		return doc ;
	}
	
	protected static List<Document> createIndexDocumentList(List<List<IndexableField>> rowList) {
		if(CollectionUtils.isBlank(rowList)) return null ;
		List<Document> list = new ArrayList<Document>() ;
		for(List<IndexableField> indexRowData : rowList) {
			Document doc = createIndexDocument(indexRowData) ;
			if(doc!=null) list.add(doc)  ;
		}
		return list ;
	}
}
