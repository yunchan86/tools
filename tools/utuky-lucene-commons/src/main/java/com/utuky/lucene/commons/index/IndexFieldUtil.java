package com.utuky.lucene.commons.index;

import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;

import com.utuky.lucene.commons.define.IndexFieldTypeConstant;

public class IndexFieldUtil {

	public IndexableField createField(String type,String indexname,Object value,Store store) {
		IndexableField field = null ;
		if(IndexFieldTypeConstant.FIELD_TEXT.equals(type)) {
			field = new TextField(indexname,(String)value,store) ;
		}else if(IndexFieldTypeConstant.FIELD_STRING.equals(type)) {
			field = new StringField(indexname,(String)value,store) ;
		}else if(IndexFieldTypeConstant.FIELD_LONGPOINT.equals(type)){
			if(value==null) {
				field = null ;
			}else if(value instanceof Long[]) {
				field = new LongPoint(indexname,(long[])value);
			}else if(value instanceof Long) {
				field = new LongPoint(indexname,(long)value);
			}else {
				field = null ;
			}
		}else if(IndexFieldTypeConstant.FIELD_INTPOINT.equals(type)){
			if(value==null) {
				field = null ;
			}else if(value instanceof Integer[]) {
				field = new IntPoint(indexname,(int[])value);
			}else if(value instanceof Integer) {
				field = new IntPoint(indexname,(int)value);
			}else {
				field = null ;
			}
		}else {
			field = new TextField(indexname,(String)value,store) ;
		}
		return field ;
	}
	
	public IndexableField createField(Object[] fieldparams) {
		IndexableField field = null ;
		String type = (String)fieldparams[0];
		String indexname = (String)fieldparams[1];
		Object value = (Object)fieldparams[2];
		Store store = (Store)fieldparams[3];
		field = this.createField(type, indexname, value, store) ;
		return field ;
	}
}
