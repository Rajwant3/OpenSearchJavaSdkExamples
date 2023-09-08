package com.java.opensearch.config;

import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

	@Value("${open.search.domain.endpoint}")
	private String aesEndpoint;

	@Value("${open.search.user.name}")
	private String userName;

	@Value("${open.search.password}")
	private String password;

	@Bean("openSearchClient")
	public OpenSearchClient opensearch() {
		final int port = 443;
		final Set<String> nodeAddresses = Set.of(aesEndpoint);

		final HttpHost[] hosts = nodeAddresses.stream().map(nodeAddress -> new HttpHost(nodeAddress, port, "https"))
				.toArray(HttpHost[]::new);
		final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(hosts[0]),
				new UsernamePasswordCredentials(userName, password));

		final RestClient restClient = RestClient.builder(hosts)
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				}).build();

		final OpenSearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		return new OpenSearchClient(transport);

	}

}
