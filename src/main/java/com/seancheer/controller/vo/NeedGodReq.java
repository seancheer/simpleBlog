package com.seancheer.controller.vo;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
@XmlRootElement(name = "NeedGodReq")
public class NeedGodReq {
	@NotNull
	private String key;

	public String getKey() {
		return key;
	}

	@XmlElement
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return " key = " + key;
	}
}
