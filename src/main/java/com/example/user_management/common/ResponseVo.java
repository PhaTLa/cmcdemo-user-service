package com.example.user_management.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseVo {
	public ResponseVo(String message) {
		this.message = message;
	}
	private String message;
	private int total;
	private List<? extends BaseVo> voList;
	
}
