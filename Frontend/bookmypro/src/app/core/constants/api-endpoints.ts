export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: '/auth/login',
    LOGOUT: '/auth/logout',
    LOGOUT_ALL: '/auth/logout-all',
    CREDENTIAL: '/auth/credential',
    AUTH_ME: '/auth/me',
    VERIFY_OTP: '/auth/otp/verify-otp',
    FORGOT_PASSWORD_SEND_OTP: "/auth/forgot-password/send-otp",
    FORGOT_PASSWORD_VALIDATE_OTP: "/auth/forgot-password/validate-otp",
    CUSTOMER_REGISTER: "/customer/onboard",
    PROVIDER_REGISTER: "/provider/onboard",
  },
  CUSTOMER: {
    CUSTOMER_PROFILE: "/customer/profile",
    CUSTOMER_ADDRESS: "/customer/address"
  },
  PROVIDER: {
    PERSONAL_PROFILE: "/provider/profile/personal",
    PROFESSIONAL_PROFILE: "/provider/profile/professional",
    EDUCATION: "/provider/profile/education",
    EXPERIENCE: "/provider/profile/experience",
    BANK: "/provider/profile/bank",
    SERVICES: "/provider/profile/services",
    AVAILABILITY: "/provider/profile/availability",
    DETAILS: "/provider/details"
  },
  SERVICES: {
    SUMMARY_GRID: "/services/summary",
    DETAILS: "/service/details"
  }

} as const;
