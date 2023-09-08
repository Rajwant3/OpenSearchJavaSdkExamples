package com.java.opensearch.controller;

import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.opensearch.client.opensearch.indices.DeleteIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.java.opensearch.service.OpenSearchService;

@RestController
public class OpenSearchController {

	@Autowired
	private OpenSearchService openSearchService;

	/**
	 * Create a open search index with specific mappings
	 */
	public CreateIndexResponse createIndex() {
		return openSearchService.createIndex();
	}

	/**
	 * Delete Elastic Search Index
	 */
	public DeleteIndexResponse deleteIndex() {
		return openSearchService.deleteIndex();
	}

	/**
	 * Upload Data on OpenSearch in bulk
	 * 
	 * @return
	 */
	public BulkResponse bulkUploadData() {
		return openSearchService.bulkUploadRequest();
	}
}
