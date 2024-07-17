package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// input : 로그인 된 사람의 userId
	// output : List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input : 파라미터들
	// output : X
	public void addPost(int userId, String userLoginId,
			String subject, String content, MultipartFile file) {
		// userLoginId는 따로 저장하려고 보내주는 값이라고 생각하면 됨.
		
		String imagePath = null;
		if(file != null) {
			// 업로드 할 이미지가 있을때에만 업로드를 해주는 것이다.
			// 이미지 업로드할 기능마다 족족 다 바꿔줘야하니, 코드가 중복되는 방식을 막으려면 class를 만들어서 보완하게 하면 된다.
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}
}
