package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {
	// private Logger log = LoggerFactory.getLogger(PostBO.class);
	// private Logger log = LoggerFactory.getLogger(this.getClass()); // 쓰고 싶은 친구에게 logger를 찍어서 보내는 역할
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// input : 로그인 된 사람의 userId
	// output : List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input : userId, postId
	// output : Post or null (단건이니까 null값이 리턴 될 수도 있다.)
	public Post getPostByPostIdUserId(int userId, int PostId) {
		return postMapper.selectPostByPostIdUserId(userId, PostId) ;
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
	
	// input : 파라미터들
	// output : X
	
	public void updatePostbyPostId(
			int userId, String loginId,
			int postId, String subject, String content,
			MultipartFile file) {
		
		// 기존 글 가져온다 (1.이미지 교체시 삭제하기 위해 2. 업데이트 대상이 있는지 확인)
		// log를 찍을 때, sysout을 사용하면 안된다. 한명당 그 서비스를 사용할때, 그 쓰레드를 하나씩 사용하기 때문에 홈페이지 자체가 느려질수도있다.
		Post post = postMapper.selectPostByPostIdUserId(userId, postId);
		if (post == null) {
			// method 설명 : 로그 레벨 DEBUG, INFO, WARN, ERROR, FATAL => 왼쪽부터 사용빈도수가 높고 오른쪽으로 갈수록 사용 빈도수가 낮다. 
			log.warn("[글 수정] post is null. userId:{}", userId, postId); // 와일드 카드 문법을 사용할때 ,를 사용한다면 따로 확인이 가능하다.
			return;
		}
		
		// 파일이 있으면
		// 1) 새 이미지 업로드 / 만약 실패했을때 바꾸지 않는다.
		// 2) 1번 단계가 성공하면 기존 이미지가 있을때 삭제
		String imagePath = null;
		
		// 파일이 있다면
		if(file != null) {
			// 새 이미지 업로드
			imagePath = fileManagerService.uploadFile(file, loginId);
			
			// 업로드 성공 시(imagePath == null이 아닐 때) 기존 이미지가 있다면 제거
			if(imagePath != null && post.getImagePath() != null) {
				// 폴더와 이미지 제거(서버에서)
				fileManagerService.deleteFile(post.getImagePath());
			}
		}
		
		// db update
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}
	
	// input : postId, userId
	// output : X
	
	public void deletePostByPostId(int postId, int userId,
			String loginId, MultipartFile file) {
		
		Post post = postMapper.selectPostByPostIdUserId(userId, postId);
		
		if (post == null) {
			log.warn("[글 삭제] post is null. userId:{}", userId, postId);
			return;
		}
		
		// 업로드 성공 시(imagePath == null이 아닐 때) 기존 이미지가 있다면 제거
		if(post.getImagePath() != null) {
			// 폴더와 이미지 제거(서버에서)
			fileManagerService.deleteFile(post.getImagePath());
		}
					
				
		// db insert
		postMapper.deletePostByPostIdUserId(postId, userId);
	}
}
