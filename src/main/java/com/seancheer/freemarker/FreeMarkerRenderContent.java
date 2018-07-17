package com.seancheer.freemarker;

import java.util.List;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 返回渲染出来的blog正文，其中对可能注入的xss进行了对应的处理
 * 
 * @author seancheer
 * @date 2018年4月14日
 */
public class FreeMarkerRenderContent implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		String content = arguments.get(0).toString();
		return content;
	}

}
