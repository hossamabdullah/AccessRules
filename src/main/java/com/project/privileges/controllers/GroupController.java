package com.project.privileges.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.privileges.dtos.MembersRepsonseDTO;
import com.project.privileges.services.GroupService;

@RestController()
@RequestMapping("/group")
public class GroupController {

	@Autowired
	GroupService groupService;

	private static final String GROUP_ADDED = "group added succesfully";
	private static final String GROUP_NOT_ADDED = "Already found group with the same name";
	private static final String OPERATION_SUCCESS = "Operation finished succesfully";

	@PutMapping()
	public String addGroup(@RequestParam("name") String name) {
		boolean isSuccessful = groupService.addGroup(name);
		if (isSuccessful) {
			return GROUP_ADDED;
		} else {
			return GROUP_NOT_ADDED;
		}
	}

	@DeleteMapping()
	public String deleteGroup(@RequestParam("name") String name) {
		groupService.deleteGroup(name);
		return OPERATION_SUCCESS;
	}

	@GetMapping()
	public MembersRepsonseDTO searchGroup(@RequestParam("key") String key) {
		MembersRepsonseDTO result = groupService.search(key);
		return result;
	}

}
