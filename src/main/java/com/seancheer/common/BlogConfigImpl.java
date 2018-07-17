package com.seancheer.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.seancheer.exception.ParseConfigException;

/**
 * blog读取配置文件相关的类
 * 
 * @author seancheer
 * @date 2018年3月20日
 */
public class BlogConfigImpl implements IBlogConfig {

	private static final Logger logger = LoggerFactory.getLogger(BlogConfigImpl.class);

	private Map<String, String> configMap;

	@Override
	public void init() throws FileNotFoundException, ParserConfigurationException, ParseConfigException {
		parseConfigFile(CONFIG_FILE_PATH);

		if (configMap.size() == 0) {
			logger.warn("Empty config!");
		}
	}

	/**
	 * 解析对应的xml
	 * 
	 * @throws ParseConfigException
	 * @throws ParserConfigurationException
	 */
	private Map<String, String> parseConfigFile(String filePath)
			throws ParseConfigException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document = null;

		try {
			builder = builderFactory.newDocumentBuilder();
			document = builder.parse(getClass().getClassLoader().getResourceAsStream(filePath));
			configMap = parseFile(document);
		} catch (SAXException e) {
			logger.error("Parse [" + filePath + "] failed!", e);
			throw new ParseConfigException(e);
		} catch (IOException e) {
			logger.error("Parse [" + filePath + "] failed!", e);
			throw new ParseConfigException(e);
		}

		return configMap;
	}

	/**
	 * 解析xml中的配置信息
	 * 
	 * @param document
	 * @return
	 * @throws ParseConfigException
	 */
	private Map<String, String> parseFile(Document document) throws ParseConfigException {
		Map<String, String> map = new HashMap<String, String>();
		// <configurations
		NodeList configs = document.getChildNodes();
		assert (configs.getLength() > 0);

		// <configuration>
		Node config = configs.item(0);
		NodeList configList = config.getChildNodes();

		for (int i = 0; i < configList.getLength(); ++i) {
			Node item = configList.item(i);
			NodeList keyValueList = item.getChildNodes();

			String key = null;
			String value = null;

			for (int j = 0; j < keyValueList.getLength(); ++j) {
				Node keyValue = keyValueList.item(j);
				String nodeName = keyValue.getNodeName();

				if (null == keyValue || nodeName.equals(TEXT)) {
					continue;
				}

				if (nodeName.equals(NAME)) {
					key = keyValue.getTextContent().trim();
				} else if (nodeName.equals(VALUE)) {
					value = keyValue.getTextContent().trim();
				} else {
					throw new ParseConfigException("Invalid item:" + nodeName);
				}
			}

			if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
				map.put(key, value);
			}
		}

		return map;
	}

	/**
	 * 通过对应的key获取value，如果没有设置，或者非法，那么取默认值
	 */
	@Override
	public String getValue(String key) {
		if (null == configMap || configMap.size() == 0)
        {
            logger.warn("Configmap is empty! Return null always!");
            return null;
        }

        return configMap.get(key);
	}

	/**
	 * 获取对应的int
	 * @param key
	 * @param defaultValue
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	@Override
	public Integer getInt(String key, Integer defaultValue, Integer minValue, Integer maxValue) {
		checkKey(key);

		Integer value = 0;
		try {
			value = Integer.parseInt(getValue(key));
		} catch (NumberFormatException e) {
			logger.info("Invalid number! Use default instead. key:" + key);
			value = defaultValue;
		}

		return (value >= minValue && value <= maxValue) ? value : defaultValue;
	}

    /**
     * 获取long值
     * @param key
     * @param defaultValue
     * @param minValue
     * @param maxValue
     * @return
     */
	@Override
	public Long getLong(String key, Long defaultValue, Long minValue, Long maxValue) {
        checkKey(key);

        Long value = 0L;
        try {
            value = Long.parseLong(getValue(key));
        } catch (NumberFormatException e) {
            logger.info("Invalid number! Use default instead. key:" + key);
            value = defaultValue;
        }

        return (value >= minValue && value <= maxValue) ? value : defaultValue;
	}

    /**
     * 获取double值
     * @param key
     * @param defaultValue
     * @param minValue
     * @param maxValue
     * @return
     */
	@Override
	public Double getDouble(String key, Double defaultValue, Double minValue, Double maxValue) {
        checkKey(key);

        Double value = 0.0;
        try {
            value = Double.parseDouble(getValue(key));
        } catch (NumberFormatException e) {
            logger.info("Invalid number! Use default instead. key:" + key);
            value = defaultValue;
        }

        return (value >= minValue && value <= maxValue) ? value : defaultValue;
	}

    /**
     * 获取float值
     * @param key
     * @param defaultValue
     * @param minValue
     * @param maxValue
     * @return
     */
	@Override
	public Float getFloat(String key, Float defaultValue, Float minValue, Float maxValue) {
        checkKey(key);

        Float value = 0.0F;
        try {
            value = Float.parseFloat(getValue(key));
        } catch (NumberFormatException e) {
            logger.info("Invalid number! Use default instead. key:" + key);
            value = defaultValue;
        }

        return (value >= minValue && value <= maxValue) ? value : defaultValue;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	private void checkKey(String key) {
		if (StringUtils.isEmpty(key)) {
			String msg = "Invalid key:" + key + " !";
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
	}

}
