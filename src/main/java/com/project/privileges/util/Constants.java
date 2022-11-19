package com.project.privileges.util;

import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;

public class Constants {
    public static final String GROUP_FIELD_NAME = "group";
    public static final String MEMBER_FIELD_NAME = "member";
    
    public static final String DEFAULT_MEMBER_VALUE = "default";
    
    public static final ClassPathResource RESOURCE = new ClassPathResource("data.json");
}
