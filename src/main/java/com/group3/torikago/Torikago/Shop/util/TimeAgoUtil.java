package com.group3.torikago.Torikago.Shop.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAgoUtil {
    public static String calculateTimeAgo(LocalDateTime orderDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(orderDate, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (days > 0) {
            return days + " days ago";
        } else if (hours > 0) {
            return hours + " hours ago";
        } else {
            return minutes + " mins ago";
        }
    }
}
