package kpi.leonchyk.diploma.util;

import kpi.leonchyk.diploma.domain.Subscription;
import kpi.leonchyk.diploma.domain.SubscriptionType;
import kpi.leonchyk.diploma.domain.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public interface Utility {
    static String trimToNull(String str) {
        if (str.trim().isEmpty()) {
            return null;
        }
        return str;
    }

    static Date getZonedDateNow() {
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
            return (Date) formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static Date formatStringToZonedDate(String date) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
            return (Date) formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static Subscription processSubscriptionPeriodAndPrice(SubscriptionType subscriptionType, User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        Calendar calendar = Calendar.getInstance();
        subscription.setSubscriptionStartDate(calendar.getTime());
        subscription.setSubscriptionType(subscriptionType);
        if (user.isSubscribed() && !user.getSubscriptions().isEmpty()) {
            Date lastSubscriptionDate = user.getSubscriptions().get(user.getSubscriptions().size() - 1).getGetSubscriptionEndDate();
            calendar.setTime(lastSubscriptionDate);
        }
        if (subscriptionType.equals(SubscriptionType.MONTHLY)) {
            subscription.setPrice(10);
            calendar.add(Calendar.MONTH, 1);
            subscription.setGetSubscriptionEndDate(calendar.getTime());
        } else if (subscriptionType.equals(SubscriptionType.ANNUAL)) {
            subscription.setPrice(100);
            calendar.add(Calendar.YEAR, 1);
            subscription.setGetSubscriptionEndDate(calendar.getTime());
        }
        return subscription;
    }

    static boolean isSubscriptionEnd(User user) {
        if (user.isSubscribed()) {
            if (user.getSubscriptions().isEmpty()) {
                return false;
            }
            Date lastSubscriptionDate = user.getSubscriptions().get(user.getSubscriptions().size() - 1).getGetSubscriptionEndDate();
            return lastSubscriptionDate.before(new Date());
        }
        return false;
    }
}
