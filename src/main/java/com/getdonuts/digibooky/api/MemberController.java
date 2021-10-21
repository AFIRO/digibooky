package com.getdonuts.digibooky.api;


import com.getdonuts.digibooky.api.dto.CreateMemberDTO;
import com.getdonuts.digibooky.api.dto.MemberDTO;
import com.getdonuts.digibooky.services.MemberService;
import com.getdonuts.digibooky.services.mapper.MemberMapper;
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
    public MemberDTO createMember(@RequestBody CreateMemberDTO DTO) {
        return memberService.saveMember(DTO);
    }

}
