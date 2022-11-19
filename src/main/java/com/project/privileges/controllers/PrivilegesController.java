package com.project.privileges.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.privileges.dtos.MembersResponseWrapper;
import com.project.privileges.services.PrivilegesService;

@RestController()
@RequestMapping("/privileges")
public class PrivilegesController {
    
    @Autowired
    private PrivilegesService privilegesService;

    @GetMapping()
    public MembersResponseWrapper getGroupAndMembersData(){
        return privilegesService.getAllMembers();
    }

}
