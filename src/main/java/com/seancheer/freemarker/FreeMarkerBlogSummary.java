package com.seancheer.freemarker;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seancheer.utils.HTML2Text;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 在freemarker模板里用来生成summary的方法
 * @author seancheer
 * @date 2018年4月14日
 */
public class FreeMarkerBlogSummary implements TemplateMethodModelEx {

	private static final Logger logger = LoggerFactory.getLogger(FreeMarkerBlogSummary.class);
	
	/**
	 * 根据对应的content生成summary
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String content = arguments.get(0).toString();
		HTML2Text converter = new HTML2Text();
		
		try {
			converter.parse(content);
		} catch (IOException e) {
			logger.error("Parse html failed!",e);
			return null;
		}
		
		return converter.getText();
	}

}
