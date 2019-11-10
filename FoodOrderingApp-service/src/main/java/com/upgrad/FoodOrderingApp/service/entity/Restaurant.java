package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurant", schema = "public")
@NamedQueries({
        @NamedQuery(name = "restaurantByName", query = "select u from Restaurant u where u.restaurant_name = :restaurant_name"),
})
public class Restaurant implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "restaurant_name")
    @NotNull
    @Size(max = 50)
    private String restaurant_name;

    @Column(name = "photo_url")
    @NotNull
    @Size(max = 255)
    private String photo_url;

    @Column(name = "customer_rating")
    @NotNull
    private BigDecimal customer_rating;

    @Column(name = "average_price_for_two")
    @NotNull
    private Integer average_price_for_two;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private Integer number_of_customers_rated;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Category> category = new ArrayList();

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

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public BigDecimal getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(BigDecimal customer_rating) {
        this.customer_rating = customer_rating;
    }

    public Integer getAverage_price_for_two() {
        return average_price_for_two;
    }

    public void setAverage_price_for_two(Integer average_price_for_two) {
        this.average_price_for_two = average_price_for_two;
    }

    public Integer getNumber_of_customers_rated() {
        return number_of_customers_rated;
    }

    public void setNumber_of_customers_rated(Integer number_of_customers_rated) {
        this.number_of_customers_rated = number_of_customers_rated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

//    public List<Category> getCategory() {
//        return category;
//    }
//
//    public void setCategory(List<Category> category) {
//        this.category = category;
//    }

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
