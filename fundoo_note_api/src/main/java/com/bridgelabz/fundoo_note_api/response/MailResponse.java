package com.bridgelabz.fundoo_note_api.response;

import org.springframework.stereotype.Component;

@Component
public class MailResponse {
public String fromMessage(String url,String token) {
	return  url +"/" +token;
}

}
