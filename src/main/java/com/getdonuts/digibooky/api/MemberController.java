package com.getdonuts.digibooky.api;


import com.getdonuts.digibooky.api.dto.CreateMemberDto;
import com.getdonuts.digibooky.api.dto.MemberDto;
import com.getdonuts.digibooky.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberDto DTO) {
        return memberService.saveMember(DTO);
    }
}
