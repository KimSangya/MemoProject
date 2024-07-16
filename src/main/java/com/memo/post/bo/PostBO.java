package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	// input : 로그인 된 사람의 userId
	// output : List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.getPostListByUserId(userId);
	}
	
	// input : subject, content, imagePath 
//	public void getPostListBySubjectContentMultipartFile(Integer userId, String subject, String content, String imagePath) {
//		postMapper.InsertPostListBySubjectContentMultipartFile(userId, subject, content, imagePath);
//	}
}
