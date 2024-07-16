package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String PostListView(HttpSession session, Model model) {
		// 로그인 여부 확인
		// int로 하는 순간 error가 날것이다. 왜냐면 int는 null값을 못받기 때문에.
		Integer userId = (Integer)session.getAttribute("userId");
		
		if(userId == null) {
			// 비로그인일때 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// DB 조회 (글목록에 대해서)
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		// 모델에 담기 (view가 있는곳)
		model.addAttribute("postList", postList);
		
		// userId가 있음으로 그냥 리턴을 하게 되는 것이다.
		return "post/postList";
	}
	
	/**
	 * 글쓰기 화면
	 * @return
	 */
	
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}
	
}
