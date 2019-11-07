package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "CUSTOMER_AUTH", schema = "public")
@NamedQueries({
        @NamedQuery(name = "customerAuthTokenByAccessToken", query = "select ct from CustomerAuthEntity ct where ct.accessToken =:accessToken")
})

public class CustomerAuthEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 200)
    private String uuid;

    @Column(name = "CUSTOMER_ID")
    @NotNull
    @Size(max = 50)
    private int cust_id;

    @Column(name = "ACCESS_TOKEN")
    @NotNull
    @Size(max = 500)
    private String accessToken;

    @Column(name = "LOGIN_AT")
    private Timestamp login_at;

    @Column(name = "LOGOUT_AT")
    private Timestamp logout_at;


    @Column(name = "EXPIRES_AT")
    private Timestamp expires_at;

    public long getId() {
            return id;
        }

    public void setId(long id) {
            this.id = id;
        }

    public String getUuid() {
            return uuid;
        }

    public void setUuid(String uuid) {
            this.uuid = uuid;
        }

    public void setCustId(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Timestamp getLogin_at() {
        return login_at;
    }

    public void setLogin_at(Timestamp login_at) {
        this.login_at = login_at;
    }

    public Timestamp getLogout_at() {
        return logout_at;
    }

    public void setLogout_at(Timestamp logout_at) {
        this.logout_at = logout_at;
    }

    public Timestamp getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Timestamp expires_at) {
        this.expires_at = expires_at;
    }


    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
