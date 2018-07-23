package com.seancheer.dao.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category_1")
public class Category1 implements BaseCategory{

	@Id
	@Column(name = "id", length = 4)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "description", length = 32)
	private String description;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP default current_timestamp")
	private Timestamp createTime;

	@Column(name = "is_del", columnDefinition = "INT default 0")
	private Integer isDel = 0;

	@OneToMany(targetEntity = Category2.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<BaseCategory> category2Set;

	public Set<BaseCategory> getCategory2Set() {
		return category2Set;
	}

	public void setCategory2Set(Set<BaseCategory> category2Set) {
		this.category2Set = category2Set;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("id = " + Integer.toString(id));
		sb.append(",name = " + name);
		sb.append(",description = " + description);
		sb.append(",createTime = " + createTime);
		sb.append(",isDel = " + isDel);
		sb.append(",category2List = " + category2Set.toString());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Set<BaseCategory> getChildren() {
		return this.category2Set;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category2Set == null) ? 0 : category2Set.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isDel == null) ? 0 : isDel.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category1 other = (Category1) obj;
		if (category2Set == null) {
			if (other.category2Set != null)
				return false;
		} else if (!category2Set.equals(other.category2Set))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDel == null) {
			if (other.isDel != null)
				return false;
		} else if (!isDel.equals(other.isDel))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}