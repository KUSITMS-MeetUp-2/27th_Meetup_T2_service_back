package com.modagbul.BE.domain.notice.service;

import com.modagbul.BE.domain.notice.dto.NoticeDto;
import com.modagbul.BE.domain.notice.dto.NoticeDto.CreateNoticeRequest;
import com.modagbul.BE.domain.notice.dto.NoticeDto.CreateNoticeResponse;
import com.modagbul.BE.domain.notice.dto.NoticeMapper;
import com.modagbul.BE.domain.notice.entity.Notice;
import com.modagbul.BE.domain.notice.repository.NoticeRepository;
import com.modagbul.BE.domain.noticeread.entity.NoticeRead;
import com.modagbul.BE.domain.noticeread.repository.NoticeReadRepository;
import com.modagbul.BE.domain.team.entity.Team;
import com.modagbul.BE.domain.team.exception.NotHavaTeamIdException;
import com.modagbul.BE.domain.team.repository.TeamRepository;
import com.modagbul.BE.domain.teammember.entity.TeamMember;
import com.modagbul.BE.domain.teammember.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeServiceImpl implements NoticeService{

    private final NoticeMapper noticeMapper;

    private final NoticeRepository noticeRepository;

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final NoticeReadRepository noticeReadRepository;

    @Override
    public CreateNoticeResponse createNotice(CreateNoticeRequest createNoticeRequest) {
        Notice notice = noticeMapper.toEntity(createNoticeRequest);
        Team team = teamRepository.findById(createNoticeRequest.getTeamId())
                .orElseThrow(() -> new NotHavaTeamIdException());
        notice.setTeam(team);
        noticeRepository.save(notice);

        List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
        teamMembers.stream().forEach(teamMember -> {
            NoticeRead noticeRead = new NoticeRead();
            noticeRead.setNotice(notice);
            noticeRead.setTeamMember(teamMember);
            noticeReadRepository.save(noticeRead);
        });

        return new CreateNoticeResponse(notice.getNoticeId());
    }
}