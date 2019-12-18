package com.wei.documentstorage.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.wei.documentstorage.config.DocumentStorageProperties;
import com.wei.documentstorage.exception.DocumentNotFoundException;
import com.wei.documentstorage.model.DocumentMetadata;
import com.wei.documentstorage.model.Document;
import com.wei.documentstorage.repository.DocumentStorageRepository;


@Service
public class DocumentStorageService implements StorageService {
	private DocumentStorageRepository docRepo;
	private final Path docStorageLocation;
	private final int docIdLength;
	
	@Autowired
	public DocumentStorageService(DocumentStorageRepository docRepo, 
			DocumentStorageProperties docStorageProperties) {
		this.docRepo = docRepo;
		this.docStorageLocation = Paths
				.get(docStorageProperties.getStorageDir())
				.toAbsolutePath()
				.normalize();
		this.docIdLength = docStorageProperties.getIdLength();
		
		try {
			Files.createDirectories(this.docStorageLocation);
		} catch (IOException e) {
			//This error is not outlined in the project specification, 
			//and thus ignore it for now
			e.printStackTrace();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document load(String docId) throws DocumentNotFoundException {	
		Optional<DocumentMetadata> documentMetadata = docRepo.findById(docId);
		
		if (!documentMetadata.isPresent()) {
			throw new DocumentNotFoundException();
		}
		
		DocumentMetadata metadata = documentMetadata.get();
		
		Path docPath = docStorageLocation
				.resolve(metadata.getName())
				.normalize();
		
		Resource resource = null;
		
		try {
			resource = new UrlResource(docPath.toUri());
			
			if (!resource.exists()) {
				throw new DocumentNotFoundException();
			}
		} catch (MalformedURLException e) {
			//This error is not outlined in the project specification, 
			//and thus ignore it for now
			e.printStackTrace();
		}
		
		return new Document(metadata, resource);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String store(MultipartFile document) {
		String docId = generateDocId();
		String docName = StringUtils
				.cleanPath(document.getOriginalFilename());
		
		try {
			Path targetLocation = docStorageLocation.resolve(docName);

			Files.copy(document.getInputStream(), targetLocation,
					StandardCopyOption.REPLACE_EXISTING);
			DocumentMetadata metaData = new DocumentMetadata();
			saveMetaData(metaData, docId, docName, 
					document.getContentType(), document.getSize());
		} catch (IOException e) {
			//This error is not outlined in the project specification, 
			//and thus ignore it for now
			e.printStackTrace();
		}
		
		return docId;		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(String docId, MultipartFile document) throws DocumentNotFoundException {
		Optional<DocumentMetadata> documentMetadata = docRepo.findById(docId);
		if (!documentMetadata.isPresent()) {
			throw new DocumentNotFoundException();
		}
		DocumentMetadata metaData = documentMetadata.get();
		
		String orgDocName = metaData.getName();
		
		String newDocName = StringUtils.cleanPath(document.getOriginalFilename());
		
		try {
			Files.delete(docStorageLocation.resolve(orgDocName));			

			Files.copy(document.getInputStream(), docStorageLocation.resolve(newDocName),
					StandardCopyOption.REPLACE_EXISTING);
			saveMetaData(metaData, docId, newDocName, 
					document.getContentType(), document.getSize());
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String docId) throws DocumentNotFoundException {
		Optional<DocumentMetadata> documentMetadata = docRepo.findById(docId);
		
		if (!documentMetadata.isPresent()) {
			throw new DocumentNotFoundException();
		}
		
		DocumentMetadata metadata = documentMetadata.get();
		
		try {
			Files.delete(docStorageLocation
					.resolve(metadata.getName()));
			docRepo.deleteById(docId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Generates a unique alphanumeric document ID with a length of 20 characters
	 * @return Generated document ID
	 */
	private String generateDocId() {
		String docId = RandomStringUtils
				.randomAlphanumeric(docIdLength);
		
		while (docRepo.existsById(docId)) {
			docId = RandomStringUtils
					.randomAlphanumeric(docIdLength);
		}
		
		return docId;
	}
	
	/**
	 * Save or update a document metadata
	 * @param metaData Document Metadata
	 * @param docId Document ID
	 * @param docName Document name
	 * @param contentType Document Content Type
	 * @param docSize Document Size
	 */
	private void saveMetaData(DocumentMetadata metaData, String docId,
			String docName, String contentType, long docSize) {
		metaData.setId(docId);
		metaData.setName(docName);
		metaData.setContentType(contentType);
		metaData.setSize(docSize);
		docRepo.save(metaData);
	}	
}
