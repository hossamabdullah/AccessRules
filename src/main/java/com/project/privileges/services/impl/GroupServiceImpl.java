package com.project.privileges.services.impl;

import org.springframework.stereotype.Service;

import com.project.privileges.services.GroupService;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;

import com.project.privileges.dtos.MemberData;
import com.project.privileges.dtos.MembersRepsonseDTO;
import com.project.privileges.dtos.MembersResponseWrapper;
import com.project.privileges.services.PrivilegesService;
import com.project.privileges.util.Constants;

@Service
public class GroupServiceImpl implements GroupService{

    @Override
    public MembersRepsonseDTO search(String key) {
    	JSONParser jsonParser = new JSONParser();
		JSONArray data = null;
        try (FileReader reader = new FileReader(Constants.RESOURCE.getFile()))
        {
            Object obj = jsonParser.parse(reader);
            data = (JSONArray) obj;
            Map<String, List<MemberData>> groupToMembers = new HashMap<>();
            List<MemberData> memberDataWrapper = new ArrayList<>();
            for(int i=0; i<data.size(); i++) {
            	JSONObject member = (JSONObject) data.get(i);
                String groupVal = (String)(member).get(Constants.GROUP_FIELD_NAME);
                MemberData m = new MemberData();
                if(groupVal.equals(key)) {
                	String memberVal = (String)(member).get(Constants.MEMBER_FIELD_NAME);
                	m.setMember(memberVal);
                	
                	List<String> rules = new ArrayList<>();
                    for(Object fieldName: member.keySet()){
                        if((Boolean)member.get((String)fieldName).equals("true")){
                            rules.add((String) fieldName);
                        }
                    }
                    m.setAvailableRules(rules);
                    
                    memberDataWrapper.add(m);
                }
            }
            if(memberDataWrapper.isEmpty())
            	return null;
            else {
            	MembersRepsonseDTO response = new MembersRepsonseDTO();
            	response.setGroup(key);
            	response.setMemberData(memberDataWrapper);
            	return response;
            }
        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public boolean addGroup(String name) {
    	JSONParser jsonParser = new JSONParser();
		JSONArray data = null;
        try (FileReader reader = new FileReader(Constants.RESOURCE.getFile()))
        {
            Object obj = jsonParser.parse(reader);
            data = (JSONArray) obj;
            for(int i=0; i<data.size(); i++) {
            	JSONObject member = (JSONObject) data.get(i);
                String groupVal = (String)(member).get(Constants.GROUP_FIELD_NAME);
                if(groupVal.equals(name)) {
                	return false;
                }
            }
        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }
        
        if(data == null)
        	return false;
        Map<String, String> newGroupDataMap = new HashMap<>();
        newGroupDataMap.put(Constants.GROUP_FIELD_NAME, name);
        newGroupDataMap.put(Constants.MEMBER_FIELD_NAME, Constants.DEFAULT_MEMBER_VALUE);
        JSONObject newGroup = new JSONObject(newGroupDataMap);
        data.add(newGroup);
        
        return saveFile(data);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void deleteGroup(String name) {
        JSONArray data = deleteGroupFromJson(name);
        saveFile(data);
    }

	private JSONArray deleteGroupFromJson(String name) {
		JSONParser jsonParser = new JSONParser();
		JSONArray resultData = new JSONArray();
        try (FileReader reader = new FileReader(Constants.RESOURCE.getFile()))
        {
            Object obj = jsonParser.parse(reader);
 
            JSONArray data = (JSONArray) obj;
            List<Integer> indecesToBeRemoved = new ArrayList<>();
            for(int i=0; i<data.size(); i++) {
            	JSONObject member = (JSONObject) data.get(i);
                String groupVal = (String)(member).get(Constants.GROUP_FIELD_NAME);
                
                if(!groupVal.equals(name)){
                	resultData.add(member);
                }
            }
            
        } catch (IOException| ParseException e) {
            e.printStackTrace();
        }
		return resultData;
	}

	private boolean saveFile(JSONArray data) {
		if(data == null)
			return false;
		
    	try(FileWriter file = new FileWriter(Constants.RESOURCE.getFile())){
    		System.out.println(data.toJSONString());
    		file.write(data.toJSONString());
        	file.close();
        	return true;
    	}catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

    
    
}
