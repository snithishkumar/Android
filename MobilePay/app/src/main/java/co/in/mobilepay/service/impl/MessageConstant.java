package co.in.mobilepay.service.impl;

/**
 * Created by Nithish on 27-02-2016.
 */
public final class  MessageConstant {
    //1.Reg - 2^1
    //2.OTP -  2^2
    //3.Login - 2^3
    //4. Purchase
    //5. History
    //6. Card


    public final static String REG_ERROR = "Something went wrong. Please try later.";
    public final static int REG_ERROR_CODE = 500;
    public final static int REG_OK = 2;

    public final static int OTP_OK = 4;
    public static final int OTP_INVALID = 401;
    public final static int OTP_ERROR_CODE = 402;
    public final static String OTP_ERROR = "Something went wrong. Please try later.";



    /** Login 2^3 **/
    public static final int LOGIN_OK = 8;
    public static final int LOGIN_INVALID_PIN = 801;
    public static final int LOGIN_INTERNAL_ERROR = 802;
    public static final int LOGIN_INVALID_MOBILE = 803;
    public final static String LOGIN_ERROR = "Something went wrong. Please try later.";
    public final static String LOGIN_INVALID_PIN_ERROR = "Invalid Pin";


    /** Mobile Number 2^4 **/
    public static final int MOBILE_VERIFY_OK = 16;
    public static final int MOBILE_VERIFY_INTERNAL_ERROR = 1602;
    public final static String MOBILE_VERIFY_INTERNAL_ERROR_MSG = "Something went wrong. Please try later.";
}
