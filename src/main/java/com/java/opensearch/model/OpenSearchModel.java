package com.java.opensearch.model;

import java.util.Map;

import org.springframework.util.DigestUtils;

import com.google.gson.annotations.Expose;

public class OpenSearchModel {

	@Expose
	private String city;

	@Expose
	private Float cpc;

	@Expose
	private String company;

	@Expose
	private Float gcpc;

	@Expose
	private String description;

	@Expose
	private String id;

	@Expose
	private String country;

	@Expose
	private String url;

	@Expose
	private String jobReference;

	@Expose
	private String uploadDate;

	@Expose
	private Map<String, Float> location;

	@Expose
	private int source;

	@Expose
	private String sourcename;

	@Expose
	private String state;

	@Expose
	private String title;

	@Expose
	private String zipcode;

	public String getUploadTime() {
		return uploadDate;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadDate = uploadTime;
	}

	public String getDescription() {
		return description;
	}

	public OpenSearchModel setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getSourcename() {
		return sourcename;
	}

	public OpenSearchModel setSourcename(String sourcename) {
		this.sourcename = sourcename;
		return this;
	}

	public int getSource() {
		return source;
	}

	public OpenSearchModel setSource(int source) {
		this.source = source;
		return this;
	}

	public String getCity() {
		return city;
	}

	public OpenSearchModel setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCompany() {
		return company;
	}

	public OpenSearchModel setCompany(String company) {
		this.company = company;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public OpenSearchModel setCountry(String country) {
		this.country = country;
		return this;
	}

	public Float getCpc() {
		return cpc;
	}

	public OpenSearchModel setCpc(Float cpc) {
		this.cpc = cpc;
		return this;
	}

	public OpenSearchModel setGcpc(Float gcpc) {
		this.gcpc = gcpc;
		return this;
	}

	public String getJobReference() {
		return jobReference;
	}

	public OpenSearchModel setJobReference(String jobReference) {
		this.jobReference = jobReference;
		return this;
	}

	public String getState() {
		return state;
	}

	public OpenSearchModel setState(String state) {
		this.state = state;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public OpenSearchModel setTitle(String title) {
		this.title = title;
		return this;

	}

	public String getUrl() {
		return url;
	}

	public OpenSearchModel setUrl(String url) {
		this.url = url;

		return this;
	}

	public String getZipcode() {
		return zipcode;
	}

	public OpenSearchModel setZipcode(String zipcode) {
		this.zipcode = zipcode;
		return this;
	}

	public String getId() {
		return id;
	}

	public OpenSearchModel setId(String id) {
		this.id = id;
		return this;
	}

	public Map<String, Float> getLocation() {
		return location;
	}

	public OpenSearchModel setLocation(Map<String, Float> location) {
		this.location = location;
		return this;
	}

	public Float getGcpc() {
		return gcpc;
	}

	/**
	 * Elastic search document unique id.
	 * 
	 * @param sourceId
	 * @return
	 */

	public String getUniqueId(int sourceId) {
		return sourceId + "_"
				+ DigestUtils.md5DigestAsHex((lowercase(company) + lowercase(title) + lowercase(zipcode)).getBytes());
	}

	/***
	 * this method will convert any string value into lowercase null as well
	 * 
	 * @param value
	 * @return
	 */
	public String lowercase(String value) {

		return String.valueOf(value).toLowerCase();
	}

}
