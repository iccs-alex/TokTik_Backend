package com.javatpoint;  

import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RestController;  

@RestController  
public class AppController   
{  

    @GetMapping("/api/hello")
    public String hello()   
    {  
        return "Hello User";  
    }


    @GetMapping("/api")
    public String api()   
    {  
        return "THIS IS HOME";  
    }

}  
