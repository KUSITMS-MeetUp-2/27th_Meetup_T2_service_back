package com.modagbul.BE.domain.user.exception;

import static com.modagbul.BE.domain.user.constant.UserConstant.UserExceptionList.NOT_HAVE_EMAIL_ERROR;

public class NotFoundUserException extends UserException {
    public NotFoundUserException(){
        super(NOT_HAVE_EMAIL_ERROR.getErrorCode(),
                NOT_HAVE_EMAIL_ERROR.getHttpStatus(),
                NOT_HAVE_EMAIL_ERROR.getMessage());
    }
}

