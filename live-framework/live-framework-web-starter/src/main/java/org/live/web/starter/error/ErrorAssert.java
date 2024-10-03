package org.live.web.starter.error;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 15:40
 */
public class ErrorAssert {

    /**
     * 判断参数不能为空
     *
     * @param obj
     * @param qiyuBaseError
     */
    public static void isNotNull(Object obj, BaseError qiyuBaseError) {
        if (obj == null) {
            throw new ErrorException(qiyuBaseError);
        }
    }

    /**
     * 判断字符串不能为空
     *
     * @param str
     * @param qiyuBaseError
     */
    public static void isNotBlank(String str, BaseError qiyuBaseError) {
        if (str == null || str.trim().length() == 0) {
            throw new ErrorException(qiyuBaseError);
        }
    }

    /**
     * flag == true
     *
     * @param flag
     * @param qiyuBaseError
     */
    public static void isTure(boolean flag, BaseError qiyuBaseError) {
        if (!flag) {
            throw new ErrorException(qiyuBaseError);
        }
    }

    /**
     * flag == true
     *
     * @param flag
     * @param qiyuErrorException
     */
    public static void isTure(boolean flag, ErrorException qiyuErrorException) {
        if (!flag) {
            throw qiyuErrorException;
        }
    }
}
