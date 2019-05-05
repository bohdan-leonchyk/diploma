package kpi.leonchyk.diploma.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "subscription")
public class Subscription implements Serializable {
    private static final long serialVersionUID = 6872036530695317149L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "subscription_type", nullable = false)
    private SubscriptionType subscriptionType;
    @Column(name = "subscription_start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Kiev")
    private Date subscriptionStartDate;
    @Column(name = "subscription_end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Kiev")
    private Date getSubscriptionEndDate;
    @Column(name = "price", nullable = false)
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Date getGetSubscriptionEndDate() {
        return getSubscriptionEndDate;
    }

    public void setGetSubscriptionEndDate(Date getSubscriptionEndDate) {
        this.getSubscriptionEndDate = getSubscriptionEndDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id == that.id &&
                price == that.price &&
                Objects.equals(user, that.user) &&
                subscriptionType == that.subscriptionType &&
                Objects.equals(subscriptionStartDate, that.subscriptionStartDate) &&
                Objects.equals(getSubscriptionEndDate, that.getSubscriptionEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, subscriptionType, subscriptionStartDate, getSubscriptionEndDate, price);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriptionType=" + subscriptionType +
                ", subscriptionStartDate=" + subscriptionStartDate +
                ", getSubscriptionEndDate=" + getSubscriptionEndDate +
                ", price=" + price +
                '}';
    }
}
