package com.project.privileges.dtos;

import java.lang.reflect.Member;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembersRepsonseDTO {
    private String group;
    private List<MemberData> memberData;
}
