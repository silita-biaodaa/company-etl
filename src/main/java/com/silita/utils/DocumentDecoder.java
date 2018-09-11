package com.silita.utils;

import com.silita.spider.common.serializable.Document;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DocumentDecoder implements Deserializer<Document> {

	public void configure(Map<String, ?> configs, boolean isKey) {
		
	}

	public Document deserialize(String topic, byte[] data) {
		if(null!=data && data.length>0) {
			return Document.BytesToObject(data);
		}
		return null;
	}

	public void close() {
		
	}
	
}
