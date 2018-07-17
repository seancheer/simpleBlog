package com.seancheer.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@Column(name = "id", length = 64)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "passage_id", length = 64)
	private Byte passageId;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP default current_timestamp")
	private Timestamp createTime;

	@Column(name = "user_id", length = 64)
	private Integer userId;

	@Column(name = "is_del", length = 32, columnDefinition = "INT default 0")
	private Integer isDel = 0;

	@Column(name = "content")
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getPassageId() {
		return passageId;
	}

	public void setPassageId(Byte passageId) {
		this.passageId = passageId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}