package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.CreateMemberDto;
import com.getdonuts.digibooky.api.dto.MemberDto;
import com.getdonuts.digibooky.domain.Address;
import com.getdonuts.digibooky.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {


    public  Member toMember(CreateMemberDto DTO){
        return new Member(DTO.getINSS(), DTO.getFirstName(), DTO.getLastname(), DTO.getEmail(), new Address(DTO.getStreet(), DTO.getHouseNumber(), DTO.getPostcode(), DTO.getCity()));
    }

    public MemberDto toDTO(Member member){
        return new MemberDto(member.getId(), member.getFirstName(), member.getLastName(), member.getEmail(), member.getAddress().getStreet(), member.getAddress().getHouseNumber(), member.getAddress().getPostalCode(), member.getAddress().getCity());
    }
}
