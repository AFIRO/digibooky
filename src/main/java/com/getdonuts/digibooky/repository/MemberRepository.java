package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemberRepository {
    Map<String, Member> members;


    public MemberRepository() {
        members = new ConcurrentHashMap<>();
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public void removeMember(String id) {
        if (members.containsKey(id))
            members.remove(id);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public void updateMember(String id, Member member) {
        if (members.containsKey(id))
            members.put(id, member);
        else
            throw new NullPointerException("This member does not exist.");
    }

    public Map<String, Member> getMembers() {
        return members;
    }
}
