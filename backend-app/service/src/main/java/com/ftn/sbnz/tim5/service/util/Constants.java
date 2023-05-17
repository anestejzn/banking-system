package com.ftn.sbnz.tim5.service.util;

public class Constants {
    //PATHS
    public static final String TEMPLATE_FILE_PATH = "./src/main/resources/emailTemplates/";

    //MESSAGES
    public static final String MISSING_ID = "Id is missing";
    public static final String WRONG_PASSWORD =
            "Password/confirm password must contain at least 8 characters. " +
                    "At least one number and one special character.";
    public static final String WRONG_EMAIL = "Email is not in the right format.";
    public static final String EMPTY_EMAIL = "Email cannot be empty.";
    public static final String TOO_LONG_EMAIL = "Email length is too long. Email cannot contain more than 60 characters.";
    public static final String WRONG_NAME = "Name must contain only letters and cannot be too long.";
    public static final String WRONG_SURNAME = "Surname must contain only letters and cannot be too long.";
    public static final String WRONG_CITY = "City must contain only letters and cannot be too long.";
    public static final String WRONG_COUNTRY = "Country must contain only letters and cannot be too long.";
    public static final String WRONG_ROLE = "Role must be selected.";
    public static final String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords don't match. Try again.";
    public static final String WRONG_SECURITY_CODE = "Security code is number greater than 0.";
    public static final String WRONG_ADDRESS = "Check your street, number, post code and country.";
    public static final String WRONG_INCOME = "Income field is not correct.";
    public static final String WRONG_ACC_TYPE_NAME = "Account type name must be selected.";
    public static final String WRONG_DOB = "Date of birth cannot be null.";
    public static final String WRONG_EMPLOYER_NAME = "Employer name must be selected.";

    public static final String ROLE_CLIENT = "ROLE_CLIENT";

    //CONSTANTS
    public static final int MIN_SECURITY_NUM = 1000;
    public static final int MAX_SECURITY_NUM = 9999;
    public static final long MIN_ACCOUNT_NUM = 1000000000000L;
    public static final long MAX_ACCOUNT_NUM = 9999999999999L;
    public static final int SALT_LENGTH = 4;
    public static final int ZERO_FAILED_ATTEMPTS = 0;
    public static final int MAX_NUM_VERIFY_TRIES = 3;
    public static final String PIO_FOND = "PIO FOND";

    //REGEX
    public static final String LEGIT_PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$";
    public static final String LEGIT_NAME_REG = "^[A-Za-z]{1,1}[a-z]{1,20}([ ]?[A-Za-z]?[a-z]{1,20}|[a-z]{1,20})$";
    public static final String LEGIT_COUNTRY_REG = "[a-zA-Z ]{2,40}";
    public static final String PIN_CODE_REG = "^\\d{4}$";
    public static final String LEGIT_RE_CITY_AND_STREET_REG = "[a-zA-Z ]{1,20}";
    public static final String POSITIVE_WHOLE_NUMBER_REG = "[1-9][0-9]*";
}