package com.seancheer.dao.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.seancheer.common.BlogConstants;

/**
 * category的接口，各个category需要实现getchildren方法
 * 
 * @author seancheer
 * @date 2018年3月26日
 */
public interface BaseCategory {
	
	static final Logger logger = LoggerFactory.getLogger(BaseCategory.class);

	Set<BaseCategory> getChildren();

	String getDescription();

	Integer getId();

	String getName();

	/**
	 * 把Category1查询到的结果，返回为json格式，
	 */
	static List<JSONObject> convertToJson(List<Category1> category1List) {
		return genJson(new HashSet<>(category1List));
	}

	/**
	 * 递归式的将category转换为jsonObject，由于category不会过深，因此 不会产生Stack Overflow的问题
	 * 
	 * @param categories
	 * @return
	 */
	static List<JSONObject> genJson(Set<BaseCategory> categories) {
		if (null == categories || categories.isEmpty()) {
			return null;
		}

		List<JSONObject> topList = new ArrayList<JSONObject>();
		int idx = 0;
		for (BaseCategory baseCategory : categories) {
			JSONObject tmp = new JSONObject();
			tmp.put("index", idx++);
			tmp.put("id", baseCategory.getId());
			tmp.put("name", baseCategory.getName());
			tmp.put("desc", baseCategory.getDescription());
			tmp.put("children", genJson(baseCategory.getChildren()));
			topList.add(tmp);
		}

		return topList;
	}
	
	/**
	 * 将传入的字符串分解为单个id，并验证各个id是否非法，
	 * categoryids的规则应当为：cateoryIndex，value
	 * @param ids
	 * @return
	 */
	static List<String> processCategoryIds(String ids)
	{
		if (null == ids || "".equals(ids.trim()))
		{
			return null;
		}
		
		String[] strIds = ids.trim().split(BlogConstants.CATEGORY_SEPERATOR);
		List<String> idList = new ArrayList<String>();
		
		for (String id : strIds) {
			if (StringUtils.isEmpty(id))
			{
				continue;
			}
			
			try
			{
				Integer.parseInt(id);
				
			}catch(NumberFormatException e)
			{
				logger.error("Invalid id! id:" + id);
				return null;
			}
			
			idList.add(id);
		}
		return idList;
	}
}
