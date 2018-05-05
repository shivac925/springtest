package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.EndResult;

@RestController
public class RestCtrl {
	
	EndResult endResult;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<EndResult> test() {
		endResult = new EndResult();
		endResult.setStatus("success");
		return new ResponseEntity<EndResult>(endResult, HttpStatus.OK);
	}
	
}
