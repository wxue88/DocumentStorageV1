package com.wei.documentstorage.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import com.wei.documentstorage.model.DocumentMetadata;
@RunWith(JUnitPlatform.class)
@DataJpaTest
public class DocumentStorageRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DocumentStorageRepository repository;
	
	@Test
	public void whenFindById_thenReturnDocMetadata() {
		//given
		DocumentMetadata metadata = new DocumentMetadata();
		metadata.setId("eylF6DYSkH8ygJLkK2EA");
		entityManager.persist(metadata);
		entityManager.flush();
		
		//when
		Optional<DocumentMetadata> result = repository.findById(metadata.getId());
		assertTrue(result.isPresent());
		
		DocumentMetadata resultMetadata = result.get();		
		//then
		assertEquals(resultMetadata.getId(), metadata.getId());				
	}
}
