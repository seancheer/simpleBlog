package com.seancheer.common;

import java.io.FileNotFoundException;

import javax.xml.parsers.ParserConfigurationException;

import com.seancheer.exception.ParseConfigException;

/**
 * config类的接口
 * 
 * @author seancheer
 * @date 2018年5月5日
 */
public interface IBlogConfig {

	static final String DOMAIN_NAME = "";

	static final String CONFIG_FILE_PATH = "blog-config.xml";
	
	static final String NAME = "name";
	 
	static final String VALUE = "value";
	
	static final String TEXT = "#text";

	static final String EMPTY = "";

	static final int DEFAULT_SUM_PER_PAGE = 10;

	static final int BLOG_TITLE_LIMIT = 30;

	static final int BLOG_CONTENT_LIMIT = 400;
	
	void init() throws FileNotFoundException, ParserConfigurationException, ParseConfigException;

	String getValue(String key);
	
	Integer getInt(String key, Integer defaultValue, Integer minValue,Integer maxValue);
	
	Long getLong(String key, Long defaultValue, Long minValue,Long maxValue);
	
	Double getDouble(String key, Double defaultValue, Double minValue,Double maxValue);
	
	Float getFloat(String key, Float defaultValue, Float minValue,Float maxValue);
}
