package com.ftn.sbnz.tim5.service.exception;


import static com.ftn.sbnz.tim5.service.util.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;

public class PasswordsDoNotMatchException extends AppException {

    public PasswordsDoNotMatchException() {
        super(PASSWORDS_DO_NOT_MATCH_MESSAGE);
    }

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
