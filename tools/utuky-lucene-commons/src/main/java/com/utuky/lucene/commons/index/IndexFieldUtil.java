package com.utuky.lucene.commons.index;

import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;

import com.utuky.lucene.commons.define.IndexFieldTypeConstant;

public class IndexFieldUtil {

	public IndexableField createField(String type,String indexname,String value,Store store) {
		IndexableField field = null ;
		if(IndexFieldTypeConstant.FIELD_TEXT.equals(type)) {
			field = new TextField(indexname,value,store) ;
		}else if(IndexFieldTypeConstant.FIELD_STRING.equals(type)) {
			field = new StringField(indexname,value,store) ;
		}else {
			field = new TextField(indexname,value,store) ;
		}
		return field ;
	}
}
