package com.memo.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class PostRestcontroller {

	@Autowired
	private PostBO postBO;
	
//	@RequestMapping("/post/create")
//	public Map<String, Object> PostCreate(
//			@RequestParam("subject") String subject,
//			@RequestParam("content") String content,
//			@RequestParam("MultipartFile") String imagepath,
//			HttpSession session
//			) {
//		
//		// db 입력
//		Integer userId = (Integer)session.getAttribute("userId");
//		try {
//			postBO.getPostListBySubjectContentMultipartFile(userId, subject, content, imagepath);
//		} catch(NullPointerException e) {
//			e.printStackTrace();
//		}
//		
//		// 리턴값
//		Map<String, Object> result = new HashMap<>();
//		result.put("code", 200);
//		result.put("result", "성공");
//		
//		return result;
//	}
}
