package com.modagbul.BE.domain.notice.comment.controller;

import com.modagbul.BE.domain.notice.comment.constant.NoticeCommentConstant;
import com.modagbul.BE.domain.notice.comment.dto.NoticeCommentDto;
import com.modagbul.BE.domain.notice.comment.service.NoticeCommentService;
import com.modagbul.BE.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Notice Comment API")
@RequestMapping("/api/v1/{teamId}/notice/{noticeId}/comment")
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;

    @ApiOperation(value = "공지 댓글 생성", notes = "공지 댓글을 생성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<NoticeCommentDto.CreateNoticeCommentResponse>> createNoticeComment(@PathVariable Long teamId, @PathVariable Long noticeId, @Valid @RequestBody NoticeCommentDto.CreateNoticeCommentRequest createNoticeCommentRequest){
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), NoticeCommentConstant.ENoticeCommentResponseMessage.CREATE_NOTICE_COMMENT_SUCCESS.getMessage(), noticeCommentService.createNoticeComment(teamId, noticeId, createNoticeCommentRequest)));
    }

    @ApiOperation(value="공지 댓글 삭제", notes="공지 댓글을 삭제합니다.")
    @DeleteMapping("/{noticeCommentId}")
    public ResponseEntity<ResponseDto> deleteNoticeComment(@PathVariable Long teamId, @PathVariable Long noticeId, @PathVariable Long noticeCommentId){
        noticeCommentService.deleteNoticeComment(teamId, noticeId, noticeCommentId);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), NoticeCommentConstant.ENoticeCommentResponseMessage.DELETE_NOTICE_COMMENT_SUCCESS.getMessage()));
    }

    @ApiOperation(value="공지 댓글 조회", notes="공지 댓글 목록을 최신순으로 조회합니다")
    @GetMapping
    public ResponseEntity<ResponseDto<List<NoticeCommentDto.GetNoticeCommentResponse>>> getNoticeComment(@PathVariable Long teamId, @PathVariable Long noticeId){
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), NoticeCommentConstant.ENoticeCommentResponseMessage.GET_NOTICE_COMMENT_SUCCESS.getMessage(), noticeCommentService.getAllNoticeCommentByNoticeId(teamId, noticeId)));
    }
}
