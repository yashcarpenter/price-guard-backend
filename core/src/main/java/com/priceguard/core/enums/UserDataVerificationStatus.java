package com.priceguard.core.enums;

public class UserDataVerificationStatus {

    public enum LinkStatus {
        NOT_LINKED,
        LINKED,
        PENDING
    }

    public enum KycStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }

    public enum OtpStatus {
        TIMEOUT,
        VERIFIED,
        REJECTED
    }

    public enum LoginResponse {
        WRONG_EMAIL,
        WRONG_PASSWORD,
        WRONG_USER_NAME,
        CORRECT
    }

}
