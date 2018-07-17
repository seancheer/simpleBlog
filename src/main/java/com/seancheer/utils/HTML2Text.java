package com.seancheer.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * 将html提取为纯文本
 * 
 * @author seancheer
 * @date 2018年4月14日
 */
public class HTML2Text extends ParserCallback {
	StringBuilder s;

	/**
	 * 解析其中的html
	 * @param in
	 * @throws IOException
	 */
	public void parse(Reader in) throws IOException {
		s = new StringBuilder();
		ParserDelegator delegator = new ParserDelegator();
		delegator.parse(in, this, Boolean.TRUE);
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}
	
	/**
	 * 传入html字符串来解析出对应的纯文本
	 * @param content
	 * @throws IOException
	 */
	public void parse(String content) throws IOException
	{
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		InputStreamReader reader = new InputStreamReader(inputStream);
		this.parse(reader);
	}

	public String getText() {
		return s.toString();
	}
}
