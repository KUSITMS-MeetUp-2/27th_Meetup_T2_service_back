package com.modagbul.BE.domain.mission.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class MissionConstant {

    @Getter
    @RequiredArgsConstructor
    public enum MissionResponseMessage {
        CREATE_MISSION_SUCCESS("미션 생성 성공"),
        CREATE_MISSION_FAIL("미션 생성 실패"),
        UPDATE_MISSION_SUCCESS("미션 수정 성공"),
        UPDATE_MISSION_FAIL("미션 수정 실패"),
        INVALID_MISSION_ID("잘못된 미션 ID 입니다.");

        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MissionExceptionList{
        MISSION_AUTH_DENIED("M0001", HttpStatus.UNAUTHORIZED, "미션 생성 및 수정 권한이 없습니다."),
        NOT_FOUND_MISSION("M0002", HttpStatus.NOT_FOUND, "해당 미션을 찾을 수 없습니다.");
        private final String errorCode;
        private final HttpStatus httpStatus;
        private final String message;
    }
}