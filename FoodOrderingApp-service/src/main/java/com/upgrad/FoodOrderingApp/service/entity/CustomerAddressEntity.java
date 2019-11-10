package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "CUSTOMER_ADDRESS", schema = "public")
@NamedQueries({
        @NamedQuery(name = "customerIdByAddress", query = "select cid from CustomerAddressEntity cid where cid.address =:address"),
        @NamedQuery(name = "addressIdByCustomer", query = "select aid from CustomerAddressEntity aid where aid.customer =:customer")
})

public class CustomerAddressEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(name = "CUSTOMER_ID")
//    @NotNull
//    private int customer_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerEntity customer;

//    @Column(name = "ADDRESS_ID")
//    @NotNull
//    private int address_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AddressEntity address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public int getCustomer_id() {
//        return customer_id;
//    }
//
//    public void setCustomer_id(int customer_id) {
//        this.customer_id = customer_id;
//    }
//
//    public int getAddress_id() {
//        return address_id;
//    }
//
//    public void setAddress_id(int address_id) {
//        this.address_id = address_id;
//    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
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
