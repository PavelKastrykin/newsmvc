package com.epam.newsportal.controller;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class EntryPoint {
	public static void main(String[] args) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
	    String hashedPass = encoder.encodePassword("newcomer", null);
	    System.out.println();
	}
}
