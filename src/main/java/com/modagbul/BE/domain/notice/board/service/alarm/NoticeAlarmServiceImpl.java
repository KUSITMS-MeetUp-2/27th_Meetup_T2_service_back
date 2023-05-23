package com.modagbul.BE.domain.notice.board.service.alarm;

import com.modagbul.BE.domain.notice.board.entity.Notice;
import com.modagbul.BE.domain.team.entity.Team;
import com.modagbul.BE.domain.team.service.validate.TeamValidationService;
import com.modagbul.BE.domain.user.entity.User;
import com.modagbul.BE.domain.user.exception.NotFoundEmailException;
import com.modagbul.BE.domain.user.repository.UserRepository;
import com.modagbul.BE.fcm.dto.FcmDto;
import com.modagbul.BE.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.modagbul.BE.fcm.constant.FcmConstant.NewUploadTitle.UPLOAD_NOTICE_NEW_TITLE;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeAlarmServiceImpl implements NoticeAlarmService{

    private final TeamValidationService teamValidationService;
    private final UserRepository userRepository;
    private final FcmService fcmService;

    /**
     * Fcm 이용해서 알림 메시지 보내는 메서드
     * @param userId
     */
    @Override
    public void sendNewUploadNoticeAlarm(Notice notice, Long teamId, Long userId){
        Team team=teamValidationService.validateTeam(teamId);
        User user=userRepository.findById(userId).orElseThrow(()->new NotFoundEmailException());
        //신규 업로드 알림이 true인지 확인
        if(user.isNewUploadPush()){
            String title=team.getName()+" "+UPLOAD_NOTICE_NEW_TITLE.getTitle();
            String message=notice.getTitle();
            FcmDto.ToSingleRequest toSingleRequest=new FcmDto.ToSingleRequest(user.getFcmToken(),title,message);
            fcmService.sendSingleDevice(toSingleRequest);
        }
    }
}