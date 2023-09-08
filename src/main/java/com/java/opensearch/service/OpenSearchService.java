package com.java.opensearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.opensearch.client.opensearch._types.mapping.DateProperty;
import org.opensearch.client.opensearch._types.mapping.FloatNumberProperty;
import org.opensearch.client.opensearch._types.mapping.GeoPointProperty;
import org.opensearch.client.opensearch._types.mapping.IntegerNumberProperty;
import org.opensearch.client.opensearch._types.mapping.KeywordProperty;
import org.opensearch.client.opensearch._types.mapping.Property;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.IndexOperation;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.java.opensearch.model.OpenSearchModel;

@Service
public class OpenSearchService {

	/**
	 * Open search client
	 */
	@Autowired
	private OpenSearchClient openSearchClient;

	/**
	 * Opensearch Index, in which we need to upload data.
	 */
	@Value("${open.search.index}")
	private String openSearchIndex;

	/**
	 * Create new index
	 * 
	 * @return
	 */
	public CreateIndexResponse createIndex() {
		try {

			/**
			 * Map Index fields with specific types.
			 */
			TypeMapping typemapping = new TypeMapping.Builder().properties(getMapping()).build();

			CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index(openSearchIndex)
					.mappings(typemapping).build();
			return openSearchClient.indices().create(createIndexRequest);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

	}

	/**
	 * Open Search Index Mappings to set dynamic properties of fields.
	 * 
	 * @return
	 */
	public Map<String, Property> getMapping() {

		Map<String, Property> map = new HashMap<>();

		Property keywordProperty = new Property.Builder().keyword(new KeywordProperty.Builder().build()).build();

		/**
		 * Keyword properties
		 */
		map.put("title", keywordProperty);
		map.put("city", keywordProperty);
		map.put("state", keywordProperty);
		map.put("zipcode", keywordProperty);
		map.put("country", keywordProperty);
		map.put("sourcename", keywordProperty);

		Property geoPointProperty = new Property.Builder().geoPoint(new GeoPointProperty.Builder().build()).build();
		map.put("location", geoPointProperty);

		Property intProperty = new Property.Builder().integer(new IntegerNumberProperty.Builder().build()).build();
		map.put("source", intProperty);

		Property floatProperty = new Property.Builder().float_(new FloatNumberProperty.Builder().build()).build();
		map.put("cpc", floatProperty);
		map.put("gcpc", floatProperty);

		Property dateProperty = new Property.Builder().date(new DateProperty.Builder().build()).build();
		map.put("uploadTime", dateProperty);

		return map;
	}

	/**
	 * Delete elastic search index completely.
	 * 
	 * @return
	 * 
	 * @return
	 */
	public DeleteIndexResponse deleteIndex() {
		try {

			DeleteIndexRequest deleteRequest = new DeleteIndexRequest.Builder().index(openSearchIndex).build();
			return openSearchClient.indices().delete(deleteRequest);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Bulk Upload Request
	 * 
	 * @return
	 * @throws IOException
	 * @throws OpenSearchException
	 */
	public BulkResponse bulkUploadRequest() {

		/**
		 * This is the list of data, which we are supposed to upload.
		 */
		List<OpenSearchModel> openSearchModels = new ArrayList<>();

		/***
		 * OpenSearch Bulk upload List
		 */
		List<BulkOperation> request = new ArrayList<>();

		/**
		 * Traverse data list.
		 */
		for (OpenSearchModel openSearchModel : openSearchModels) {
			/**
			 * Add Each Data Object, in index with unique document id.
			 */
			IndexOperation<OpenSearchModel> indexOperation = new IndexOperation.Builder<OpenSearchModel>()
					.index(openSearchIndex).id(openSearchModel.getUniqueId(openSearchModel.getSource())) // Id should be
																											// unique
																											// for each
																											// document
					.document(openSearchModel).build();

			request.add(new BulkOperation.Builder().index(indexOperation).build());
		}

		try {
			return openSearchClient.bulk(new BulkRequest.Builder().operations(request).build());
		} catch (OpenSearchException | IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}
