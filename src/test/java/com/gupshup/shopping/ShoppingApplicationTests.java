package com.gupshup.shopping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import java.util.HashMap;

@SpringBootTest
class ShoppingApplicationTests {

	@Test
	void contextLoads() {
		 System.out.println("++++++++++++++++++++++++++++++++++++");
		 System.out.println("test components");
	}

	@BeforeAll
	static void setup() {
	    System.out.println("@BeforeAll ++++++++++++++++ executes once before all test methods in this class");
	}

	@PostMapping("/testlogin")
    public Map<String,String> Login() {

        Map<String,String> userDetail =  new HashMap<String,String>();

        userDetail.put("user_token","testing user token");
        userDetail.put("isadmin","1");
        System.out.println(userDetail);

        return userDetail;
    }

}
