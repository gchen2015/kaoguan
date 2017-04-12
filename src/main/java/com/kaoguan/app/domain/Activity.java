package com.kaoguan.app.domain;

import com.kaoguan.app.domain.enumeration.ActivityType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "originaztion")
    private String originaztion;

    @Column(name = "age_ranger")
    private String ageRanger;

    @Column(name = "price")
    private String price;

    @Column(name = "remark")
    private String remark;

    @Column(name = "location_city")
    private String locationCity;

    @Column(name = "address")
    private String address;

    @Column(name = "star_count")
    private Integer starCount;

    @Column(name = "join_person_count")
    private Integer joinPersonCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "image_1")
    private String image1;

    @Column(name = "image_2")
    private String image2;

    @Column(name = "image_3")
    private String image3;

    @Column(name = "image_4")
    private String image4;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "date_time")
    private ZonedDateTime datetime;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @ManyToOne
    private User creator;

    @ManyToOne
    private User updator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Activity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginaztion() {
        return originaztion;
    }

    public Activity originaztion(String originaztion) {
        this.originaztion = originaztion;
        return this;
    }

    public void setOriginaztion(String originaztion) {
        this.originaztion = originaztion;
    }

    public String getAgeRanger() {
        return ageRanger;
    }

    public Activity ageRanger(String ageRanger) {
        this.ageRanger = ageRanger;
        return this;
    }

    public void setAgeRanger(String ageRanger) {
        this.ageRanger = ageRanger;
    }

    public String getPrice() {
        return price;
    }

    public Activity price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public Activity remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public Activity locationCity(String locationCity) {
        this.locationCity = locationCity;
        return this;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getAddress() {
        return address;
    }

    public Activity address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public Activity starCount(Integer starCount) {
        this.starCount = starCount;
        return this;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getJoinPersonCount() {
        return joinPersonCount;
    }

    public Activity joinPersonCount(Integer joinPersonCount) {
        this.joinPersonCount = joinPersonCount;
        return this;
    }

    public void setJoinPersonCount(Integer joinPersonCount) {
        this.joinPersonCount = joinPersonCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Activity commentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getImage1() {
        return image1;
    }

    public Activity image1(String image1) {
        this.image1 = image1;
        return this;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public Activity image2(String image2) {
        this.image2 = image2;
        return this;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public Activity image3(String image3) {
        this.image3 = image3;
        return this;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public Activity image4(String image4) {
        this.image4 = image4;
        return this;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public Activity activityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public Activity delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public Activity datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Activity createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public Activity updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public User getCreator() {
        return creator;
    }

    public Activity creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public User getUpdator() {
        return updator;
    }

    public Activity updator(User user) {
        this.updator = user;
        return this;
    }

    public void setUpdator(User user) {
        this.updator = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", originaztion='" + originaztion + "'" +
            ", ageRanger='" + ageRanger + "'" +
            ", price='" + price + "'" +
            ", remark='" + remark + "'" +
            ", locationCity='" + locationCity + "'" +
            ", address='" + address + "'" +
            ", starCount='" + starCount + "'" +
            ", joinPersonCount='" + joinPersonCount + "'" +
            ", commentCount='" + commentCount + "'" +
            ", image1='" + image1 + "'" +
            ", image2='" + image2 + "'" +
            ", image3='" + image3 + "'" +
            ", image4='" + image4 + "'" +
            ", activityType='" + activityType + "'" +
            ", delFlag='" + delFlag + "'" +
            ", datetime='" + datetime + "'" +
            ", createTime='" + createTime + "'" +
            ", updateTime='" + updateTime + "'" +
            '}';
    }
}
