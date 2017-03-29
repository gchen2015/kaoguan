package com.kaoguan.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PreferActivity.
 */
@Entity
@Table(name = "prefer_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreferActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "create_date_time")
    private ZonedDateTime createDateTime;

    @ManyToOne
    private User perferUser;

    @ManyToOne
    private Activity activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public PreferActivity delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public PreferActivity createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public User getPerferUser() {
        return perferUser;
    }

    public PreferActivity perferUser(User user) {
        this.perferUser = user;
        return this;
    }

    public void setPerferUser(User user) {
        this.perferUser = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public PreferActivity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreferActivity preferActivity = (PreferActivity) o;
        if (preferActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, preferActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreferActivity{" +
            "id=" + id +
            ", delFlag='" + delFlag + "'" +
            ", createDateTime='" + createDateTime + "'" +
            '}';
    }
}
