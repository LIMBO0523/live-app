package org.live.user.provider.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author LIMBO0523
 * @Date 2024/10/2 10:54
 */
public class TimeUtils {
    public static int createRandomExpireTime(int minutes) {
        int time = ThreadLocalRandom.current().nextInt(1000);
        return time + 60 * minutes;
    }
}
