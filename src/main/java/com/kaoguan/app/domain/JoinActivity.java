package com.kaoguan.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JoinActivity.
 */
@Entity
@Table(name = "join_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JoinActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date_time")
    private ZonedDateTime createDateTime;

    @Column(name = "del_flag")
    private Integer delFlag;

    @ManyToOne
    private User joinUser;

    @ManyToOne
    private Activity activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public JoinActivity createDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public JoinActivity delFlag(Integer delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public User getJoinUser() {
        return joinUser;
    }

    public JoinActivity joinUser(User user) {
        this.joinUser = user;
        return this;
    }

    public void setJoinUser(User user) {
        this.joinUser = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public JoinActivity activity(Activity activity) {
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
        JoinActivity joinActivity = (JoinActivity) o;
        if (joinActivity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, joinActivity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JoinActivity{" +
            "id=" + id +
            ", createDateTime='" + createDateTime + "'" +
            ", delFlag='" + delFlag + "'" +
            '}';
    }
}
