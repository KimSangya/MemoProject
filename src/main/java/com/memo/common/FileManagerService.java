package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // post 도메인을 스프링 빈으로 만들어야 할 때, 내서버에서 다른 서버가 요청할때, server to server를 할 때, 스프링 빈이어야한다.
public class FileManagerService {
	// 자바 빈인가 스프링 빈인가를 확인할때 생각을 해야한다.
	// new로 안하고 스프링이 관여하는 빈으로 하면 좋겠다. 그러면 autowired를 사용하면 사용할수있게 되는 친구
	// configuation : 설정을 만들때 하는 빈을 만들게 되는 것이구.
	
	// 실제 업로드가 된 이미지가 저장될 서버의 경로
	// 학원, 상수라는 의미, 깃이 관리하지 않는 공간으로 넣게 될 것이다. 맨 뒤에 / 주의 꼭 붙어야함!!!!!!!!!!!!!
	public static final String FILE_UPLOAD_PATH = "D:\\kimgeonho\\6_spring_project\\memo\\memo_workspace\\images/";
	// public static final String FILE_UPLOAD_PATH = ""; // 집, 상수라는 의미 
	
	// input : 업로드할 파일 MultipartFile, userLoginId (userId로 해도 되는데, 일부로 보이는 값으로 해본것이다.)
	// output : 업로드가 끝나고 그 주소가 뭔지? String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더(디렉토리) 생성
		// 예 : aaaa_1734893459/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis(); // 밀리세컨드로 숫자를 나타나게 하는 함수.
		//D:\\kimgeonho\\6_spring_project\\memo\\memo_workspace\\images/aaaa_1734893459/sun.png
		String filePath = FILE_UPLOAD_PATH + directoryName + "/";
		
		// 업로드를 할것이고, 폴더를 생성하게 만들것이다.
		File directiory = new File(filePath);
		if(directiory.mkdir() == false) { // 폴더 생성시 실패하면 경로를 null로 리턴
			// 만약 실패를 할 경우 글쓰기는 성공시키고, 파일만 null로 넘기게 된다.
			return null;
		}
		
		// 파일 업로드가 진행
		try {
			byte[] bytes = file.getBytes();
			// 한글 이미지를 올리게 되면 올려지지 않게 된다. 
			// 이 이름이 한글을 영문으로 해야함 
			// 한글명으로 된 이미지는 업로드 불가하므로 나중에 영문자로 바꾸기!!!!!!!!!!
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 파일 업로드가 실패시 경로는 null로 리턴을 해주게 된다.
		}
		// 파일업로드가 성공하면 이미지 url path를 리턴
		// 주소는 이렇게 될 것이다.(예언)
		// /images/aaaa_1734893459/sun.png 이런식으로 리턴을 하게 만들 것이다.
		return "/images/" + directoryName + "/" + file.getOriginalFilename(); // WAS와 Images 폴더와의 연결이 안되어있을 경우에는 따로 업로드는 성공하지만, 나중에 리턴할 파일을 할수가없다.
	}
	
}
