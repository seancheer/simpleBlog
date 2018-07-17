package com.seancheer.controller.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class NewBlogReq {

	@NotNull
	@NotEmpty
	private String blogTitle;
	
	@NotNull
	@NotEmpty
	private String blogContent;
	
	@NotNull
	@NotEmpty
	private String select0;
	
	@NotNull
	@NotEmpty
	private String select1;

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public String getSelect0() {
		return select0;
	}

	public void setSelect0(String select0) {
		this.select0 = select0;
	}

	public String getSelect1() {
		return select1;
	}

	public void setSelect1(String select1) {
		this.select1 = select1;
	}
	
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("[blogTitle=").append(blogTitle);
		sb.append(",blogContent=").append(blogContent.substring(0, 50));
		sb.append(",select0=").append(select0);
		sb.append(",select1=").append(select1);
		sb.append("]");
		return sb.toString();
	}
}
