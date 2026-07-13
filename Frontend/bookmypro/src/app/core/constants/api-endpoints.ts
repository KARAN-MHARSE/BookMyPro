export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: '/auth/login',
    LOGOUT: '/auth/logout',
    LOGOUT_ALL: '/auth/logout-all',
    CREDENTIAL: '/auth/credential',
    VERIFY_OTP: '/auth/otp/verify-otp',
    FORGOT_PASSWORD_SEND_OTP: "/auth/forgot-password/send-otp",
    FORGOT_PASSWORD_VALIDATE_OTP: "/auth/forgot-password/validate-otp",
    CUSTOMER_REGISTER: "/customer/onboard",
    PROVIDER_REGISTER: "",
  },
  CUSTOMER:{
    CUSTOMER_PROFILE: "/customer/profile",
    CUSTOMER_ADDRESS:"/customer/address"
  }

} as const;
