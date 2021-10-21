package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateMemberDTO;
import com.getdonuts.digibooky.api.dto.MemberDTO;
import com.getdonuts.digibooky.domain.Member;
import com.getdonuts.digibooky.repository.MemberRepository;
import com.getdonuts.digibooky.services.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository repo;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberRepository repo, MemberMapper memberMapper) {
        this.repo = repo;
        this.memberMapper = memberMapper;
    }

    private Member createMember(CreateMemberDTO DTO) {
        if (isINSSunique(DTO.getINSS()) && validateMail(DTO.getEmail()) && validateInputs(DTO.getLastname()) && validateInputs(DTO.getCity()))
            return memberMapper.toMember(DTO);
        else
            throw new IllegalArgumentException("Inputs were not valid");
    }

    private boolean isEmailUnique(String email) {
        List<String> allMemberEmails = repo.getMembers().stream().map(Member::getEmail).collect(Collectors.toList());
        if (allMemberEmails.contains(email))
            throw new IllegalArgumentException("This e-mail is already used.");
        else
            return true;
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() || email.isBlank())
            return false;

        Pattern regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(email);

        if (matcher.find())
            return true;
        else
            throw new IllegalArgumentException("This e-mail is not valid.");
    }

    private boolean isINSSunique(String inss) {
        List<String> allMemberINSS = repo.getMembers().stream().map(Member::getINSS).collect(Collectors.toList());
        if (allMemberINSS.contains(inss))
            throw new IllegalArgumentException("INSS is already used.");
        else
            return true;
    }

    private boolean validateMail(String email) {
        return isEmailValid(email) && isEmailUnique(email);
    }


    private boolean validateInputs(String string) {
        if (string == null || string.isEmpty() || string.isBlank())
            return false;
        return true;
    }

    public MemberDTO saveMember(CreateMemberDTO createMemberDTO) {
        Member createdMember = createMember(createMemberDTO);
        return memberMapper.toDTO(repo.addMember(createdMember));
    }
}
