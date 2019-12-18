package com.wei.documentstorage.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="No document found with this ID")
public class DocumentNotFoundException extends Exception {
	private static final long serialVersionUID = 5055092613941158868L;
}
