package com.modagbul.BE.domain.vote.board.service.alarm;

import com.modagbul.BE.domain.team.entity.Team;
import com.modagbul.BE.domain.team.service.validate.TeamValidationService;
import com.modagbul.BE.domain.team_member.service.TeamMemberService;
import com.modagbul.BE.domain.user.entity.User;
import com.modagbul.BE.domain.user.exception.NotFoundEmailException;
import com.modagbul.BE.domain.user.repository.UserRepository;
import com.modagbul.BE.domain.vote.board.entity.Vote;
import com.modagbul.BE.fcm.dto.FcmDto;
import com.modagbul.BE.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.modagbul.BE.fcm.constant.FcmConstant.NewUploadTitle.UPLOAD_VOTE_NEW_TITLE;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteAlarmServiceImpl implements VoteAlarmService {

    private final TeamValidationService teamValidationService;
    private final UserRepository userRepository;
    private final TeamMemberService teamMemberService;
    private final FcmService fcmService;

    @Override
    public void sendNewUploadVoteAlarm(Vote vote, Long teamId, Long userId) {
        Team team = teamValidationService.validateTeam(teamId);
        User loggedInUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundEmailException());
        if (loggedInUser.isNewUploadPush()) {
            String title = team.getName() + " " + UPLOAD_VOTE_NEW_TITLE.getTitle();
            String message = vote.getTitle();
            Optional<List<String>> fcmTokens = teamMemberService.getTeamMemberFcmToken(teamId, userId);
            if(fcmTokens.isPresent() && !fcmTokens.get().isEmpty()) {
                FcmDto.ToMultiRequest toMultiRequest = new FcmDto.ToMultiRequest(fcmTokens.get(), title, message);
                fcmService.sendMultipleDevices(toMultiRequest);
            }
        }
    }
}
