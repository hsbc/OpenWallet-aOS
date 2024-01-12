package com.openwallet.constants

const val PARAMS_ACTIVITY_ID = "activityId"
const val PARAMS_LEARN_ITEM = "learnItem"

const val LEARN_STATUS_COMPLETED_FAILED = "COMPLETED_FAILED"
const val LEARN_STATUS_COMPLETED_SUCCESSFUL = "COMPLETED_SUCCESSFUL"
const val LEARN_STATUS_UNINVOLVED = "UNINVOLVED"

const val PARAMS_ACTION_FROM = "from"
const val PARAMS_ACTION_FROM_REGISTER_SUCCESS = "registerSuccess"
const val PARAMS_ACTION_FROM_REGISTER_FAIL = "registerFail"
const val PARAMS_ACTION_FROM_LOGIN_FAIL = "loginFail"
const val PARAMS_ACTION_FROM_DELETE_PROFILE = "deleteProfile"

const val PARAMS_ACTION_FROM_FIND_NAME_SUCCESS = "findedName"
const val PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS_WITH_LOGIN = "reset_password_success_with_login"
const val PARAMS_ACTION_FROM_RESET_PASSWORD_SUCCESS = "reset_password_success"
const val PARAMS_ACTION_FROM_CHANGE_PASSWORD_SUCCESS = "change_password_success"

const val PARAMS_ACTION_FROM_REGISTER_FIAL = "welcome"
const val PARAMS_ACTION_FROM_ERROR_VERIFICATION_CODE = "errorVerificationCode"
const val PARAMS_ACTION_FROM_REGISTER_VERIFY_EMAIL = "registerVerifyEmail"
const val PARAMS_ACTION_FROM_REGISTER_VERIFY_PHONE = "registerVerifyPhone"

const val PARAMS_ERROR_MESSAGE = "errorMessage"
const val PARAMS_ACTION_FROM_CHANGE_PASSWORD = "changePassword"
const val PARAMS_ACTION_FROM_LEARN = "learn"
const val PARAMS_ACTION_FROM_RESET_PASSWORD = "resetPassword"
const val PARAMS_ACTION_FROM_CHANGE_EMAIL = "changeEmail"
const val PARAMS_WALLET_DETAIL = "walletDetail"
const val PARAMS_LOGIN_NAME = "loginUserName"
const val PARAMS_LOGIN_EMAIL = "loginUserEmail"
const val PARAMS_LOGIN_PHONE = "loginUserPhone"
const val PARAMS_MASKED_EMAIL = "maskedEmail"
const val PARAMS_MASKED_PHONE = "maskedPhone"
const val PARAMS_REGISTER_NAME = "registerName"
const val PARAMS_REGISTER_PASSWORD = "registerPassword"
const val PARAMS_REGISTER_EMAIL = "registerEmail"
const val PARAMS_FIND_NAME_EMAIL = "findNameEmail"
const val PARAMS_FIND_NAME_PHONE = "findNamePhone"
const val PARAMS_FIND_NAME_PHONE_COUNTRY_CODE = "findNamePhoneCode"
const val PARAMS_CHANGE_PASSWORD_EMAIL = "changePasswordEmail"
const val PARAMS_CHANGE_PASSWORD_PHONE = "changePasswordPhone"
const val PARAMS_CHANGE_PASSWORD_PHONE_COUNTRY_CODE = "changePasswordPhoneCode"
const val PARAMS_REGISTER_PHONE = "registerPhone"
const val PARAMS_REGISTER_PHONE_COOUNTRY_CODE = "registerPhoneCode"
const val PARMAS_FINDED_NAME = "findedName"
const val PARAMS_VERIFY_CODE = "verifyCode"
const val PARAMS_IS_LAST_QUIZ = "isLastQuiz"
const val PARAMS_RESET_PASSWORD_EMAIL = "resetPasswordEmail"
const val PARAMS_RESET_PASSWORD_PHONE = "resetPasswordPhone"
const val PARAMS_RESET_PASSWORD_PHONE_COUNTRY_CODE = "resetPasswordPhoneCode"


const val PARAMS_ACTION_FROM_WALLET_DETAIL = "walletDetail"

const val MAIN_PARAMS_TAB = "tab"

const val HEADER_TOKEN_START = "OH "

const val NOTIFICATION_STATUS_UNREAD = "UNREAD"
const val NOTIFICATION_STATUS_READ = "READ"

const val ICON_RESOURCE_PREFIX = "ic_"
const val ICON_RESOURCE_PATH = "drawable"
const val ICON_RESOURCE_AVATAR_DEFAULT = "avatar_0"

// product environment
const val RETROFIT_HOST = ""
const val RETROFIT_IPFS = "RETROFIT_IPFS"
const val RETROFIT_IPFS_HOST = ""
const val IPFS_PATH = "ipfs/"
const val IPFS_PREFIX = "ipfs://"

const val ASSET_TYPE_ERC721 = "erc721"

const val GMT_8 = "(GMT+8)"
const val TIMEZONE_GMT_8 = "GMT+8:00"
const val TIME_PATTERN_NOTIFICATION = "dd, MMM yyyy"

const val RESND_EMAIL_MILLI_SECONDS: Long = 5 * 60 * 1000
const val RESND_PHONE_MILLI_SECONDS: Long = 60 * 1000

// Error Message
const val ERROR_MESSAGE_EMAIL_FORMAT = "Error: Email format is not correct."
const val ERROR_MESSAGE_EMAIL_FORMAT_ONE = "Email format not correct"
const val ERROR_MESSAGE_EMAIL_USED = "Error: Email is already in use!"
const val ERROR_MESSAGE_CAPTCHA_INCORRECT = "Error: Email captcha is incorrect"
const val ERROR_MESSAGE_SEND_EMAIL_CAPTCHA_FAIL = "Error: problems occured in sending email captcha"
const val ERROR_MESSAGE_USER_NAME_EXISTED = "User name already exists"
const val ERROR_MESSAGE_USER_NAME_FORMAT_INVALID =
    "Accept combination of lower and/or upper case letters, numbers, or underscore(_) only."

//Email Regex
const val EMAIL_REGEX = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$"
const val USERNAME_REGEX = "(?=.{6,30}$)^[a-zA-Z0-9_]*$"
const val ERROR_CODE_REGEX = "0x[0-9A-Fa-f]+"

//Area Code Prefix
const val AREA_CODE_PREFIX = "+"


//Auto Logout Timer
const val LOGOUT_TIME: Long = 60 * 20 * 1000
const val LOGOUT_TIME_TEST = 12 * 1000L

const val TAB_INDEX_HOME = 0
const val TAB_INDEX_WALLET = 1
const val TAB_INDEX_PROFILE = 2

//Accepted Phone String
const val ACCEPTED_PHONE_STRING = "0123456789"

