package com.wei.documentstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wei.documentstorage.model.DocumentMetadata;
@Repository
public interface DocumentStorageRepository extends JpaRepository<DocumentMetadata, String> {

}
