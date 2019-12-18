package com.wei.documentstorage.model;

import org.springframework.core.io.Resource;

public class Document {
	private DocumentMetadata metadata;
	
	private Resource resource;
	
	public Document() {}
	
	public Document(DocumentMetadata metadata, Resource resource) {
		this.metadata = metadata;
		this.resource = resource;
	}

	public DocumentMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(DocumentMetadata metadata) {
		this.metadata = metadata;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
