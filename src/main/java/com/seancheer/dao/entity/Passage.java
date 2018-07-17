package com.seancheer.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passage")
public class Passage {
	public static final int MIN_CATEGORY = 1;

	public static final int MAX_CATEGORY = 4;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 64)
	private Integer id;

	@Column(name = "title", length = 64)
	private String title;

	@Column(name = "user_id", length = 64)
	private Integer userId;

	@Column(name = "category_1_id")
	private String category1Id;

	@Column(name = "category_2_id")
	private String category2Id;

	@Column(name = "category_3_id")
	private String category3Id;

	@Column(name = "category_4_id")
	private String category4Id;

	@Column(name = "comment_count", columnDefinition = "INT default 0")
	private Integer commentCount = 0;

	@Column(name = "read_count", columnDefinition = "INT default 0")
	private Integer readCount = 0;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP default current_timestamp")
	private Timestamp createTime;

	@Column(name = "last_modify_time", columnDefinition = "TIMESTAMP default current_timestamp")
	private Timestamp lastModifyTime;

	@Column(name = "is_del", columnDefinition = "BIT default 0")
	private Integer isDel = 0;

	@Column(name = "content")
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCategory1Id() {
		return category1Id;
	}

	public void setCategory1Id(String category1Id) {
		this.category1Id = category1Id;
	}

	public String getCategory2Id() {
		return category2Id;
	}

	public void setCategory2Id(String category2Id) {
		this.category2Id = category2Id;
	}

	public String getCategory3Id() {
		return category3Id;
	}

	public void setCategory3Id(String category3Id) {
		this.category3Id = category3Id;
	}

	public String getCategory4Id() {
		return category4Id;
	}

	public void setCategory4Id(String category4Id) {
		this.category4Id = category4Id;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public static boolean isCategoryValid(String index) {
		int idx = Integer.parseInt(index);
		if (MIN_CATEGORY <= idx && idx <= MAX_CATEGORY) {
			return true;
		}

		return false;
	}
}