package com.kaoguan.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A StarActivity.
 */
@Entity
@Table(name = "star_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StarActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "create_date_time")
    private ZonedDateTime createDateTime;

    @ManyToOne
    private User user;

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

    public StarActivity delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public StarActivity createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public User getUser() {
        return user;
    }

    public StarActivity user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public StarActivity activity(Activity activity) {
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
        StarActivity starActivity = (StarActivity) o;
        if (starActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, starActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StarActivity{" +
            "id=" + id +
            ", delFlag='" + delFlag + "'" +
            ", createDateTime='" + createDateTime + "'" +
            '}';
    }
}
