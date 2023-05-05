package com.modagbul.BE.domain.usermission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserMissionStatusDto {

    private String title;
    private String dueTo; // 남은 기간
    private int completeUser;
    private int incompleteUser;

    private List<UserMissionListDto> CompleteList;
    private List<UserMissionListDto> IncompleteList;

    public UserMissionStatusDto(String title, List<UserMissionListDto> completeList, List<UserMissionListDto> incompleteList) {
        this.title = title;
        CompleteList = completeList;
        IncompleteList = incompleteList;
    }

    public void setUserNum(int completeUser, int incompleteUser) {
        this.completeUser = completeUser;
        this.incompleteUser = incompleteUser;

    }

    public void setDueTo(String dueTo) {
        this.dueTo = dueTo;
    }
}