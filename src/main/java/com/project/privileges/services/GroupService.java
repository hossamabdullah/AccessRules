package com.project.privileges.services;

import com.project.privileges.dtos.MembersRepsonseDTO;

public interface GroupService {
	MembersRepsonseDTO search(String key);
    void deleteGroup(String name);
    boolean addGroup(String name);
}
