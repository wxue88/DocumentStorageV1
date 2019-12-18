package com.wei.documentstorage.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class DocumentMetadata {
	@Id
	@ApiModelProperty(notes="Document ID")
	private String id;
	
	@ApiModelProperty(notes="Document Name")
	private String name;
	
	@ApiModelProperty(notes="Document Size")
	private long size;
	
	@ApiModelProperty(notes="Document Content Type")
	private String contentType;
	
	public DocumentMetadata() {}
	
	public DocumentMetadata(String id, String name, String contentType, long size) {
		this.id = id;
		this.name = name;
		this.contentType = contentType;
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}		
}
