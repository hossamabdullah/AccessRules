package com.project.privileges.services.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.springframework.stereotype.Service;

import com.project.privileges.dtos.MemberData;
import com.project.privileges.dtos.MembersRepsonseDTO;
import com.project.privileges.dtos.MembersResponseWrapper;
import com.project.privileges.services.PrivilegesService;
import com.project.privileges.util.Constants;

@Service
public class PrivilegesServiceImpl implements PrivilegesService{

    @SuppressWarnings("unchecked")
	@Override
    public MembersResponseWrapper getAllMembers() {
        JSONParser jsonParser = new JSONParser();
        ClassPathResource resource = new ClassPathResource("data.json"); 
        try (FileReader reader = new FileReader(resource.getFile()))
        {
            Object obj = jsonParser.parse(reader);
 
            JSONArray data = (JSONArray) obj;
            Map<String, List<MemberData>>  groupToMembers = new HashMap<>();
            for(int i=0; i<data.size(); i++) {
            	JSONObject member = (JSONObject) data.get(i);
                String groupVal = (String)(member).get(Constants.GROUP_FIELD_NAME);
                String memberVal = (String)(member).get(Constants.MEMBER_FIELD_NAME);
                
                
                List<String> rules = new ArrayList<>();
                for(Object fieldName: member.keySet()){
                    if((Boolean)member.get((String)fieldName).equals("true")){
                        rules.add((String) fieldName);
                    }
                }

                MemberData dto = new MemberData();
                dto.setMember(memberVal);
                dto.setAvailableRules(rules);

                if(!groupToMembers.containsKey(groupVal)){
                    groupToMembers.put(groupVal, new ArrayList<>());
                }
                if(!dto.getMember().equals(Constants.DEFAULT_MEMBER_VALUE))
                	groupToMembers.get(groupVal).add(dto);
                

            }

            List<MembersRepsonseDTO> result = new ArrayList<>();
            for(String key: groupToMembers.keySet()){
                MembersRepsonseDTO group = new MembersRepsonseDTO();
                group.setGroup(key);
                group.setMemberData(groupToMembers.get(key));

                result.add(group);
                
            }
            return new MembersResponseWrapper(result); 
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
