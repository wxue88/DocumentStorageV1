package com.wei.documentstorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="document")
public class DocumentStorageProperties {

	private String storageDir;
	
	private int idLength;

	public String getStorageDir() {
		return storageDir;
	}

	public void setStorageDir(String storageDir) {
		this.storageDir = storageDir;
	}

	public int getIdLength() {
		return idLength;
	}

	public void setIdLength(int idLength) {
		this.idLength = idLength;
	}

	
}
