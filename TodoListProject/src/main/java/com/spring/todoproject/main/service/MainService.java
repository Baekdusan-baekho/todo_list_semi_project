package com.spring.todoproject.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.todoproject.main.dto.MostLikeRecomdResponseDTO;
import com.spring.todoproject.main.dto.TodoRequestDTO;
import com.spring.todoproject.main.dto.TodoResponseDTO;
import com.spring.todoproject.main.entity.Todo;
import com.spring.todoproject.main.mapper.IMainMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
	
	private final IMainMapper mapper;
	
	
	// 가장 많은 좋아요를 받은 추천의 board_bno를 3개 받아온 뒤, 해당 bno의 board 상세정보를 가져옴
	public List<MostLikeRecomdResponseDTO> showMostLike() {
		
		List<MostLikeRecomdResponseDTO> dtoList = new ArrayList<>();
		List<MostLikeRecomdResponseDTO> list =  mapper.mostLikeBno();
		
		for(MostLikeRecomdResponseDTO l : list) {
			MostLikeRecomdResponseDTO dto = mapper.getMostLikeData(l.getBoardBno());
			
			dto.setBoardLikeCnt(l.getBoardLikeCnt());
			dtoList.add(dto);	
		}
			
		return dtoList;	
	}


	public String getNickname(String userId) {
		String test = mapper.getNickname(userId);
		return test;
	}


	public String insertMyTodo(TodoRequestDTO dto) {
		Todo entity = new Todo(dto);
		mapper.insertMyTodo(entity);
		
		// 마지막 시퀀스값 반환
		return mapper.getlastTodoNo();
		
	}

	// todo의 checked 값을 변경하기 위한 서비스
	public void updateMyTodoChk(TodoRequestDTO dto) {
		Todo entity = new Todo(dto, dto.isChkBtn());
		if(dto.isChkBtn()) {			
			mapper.updateMyTodoChkO(entity);
		} else {
			mapper.updateMyTodoChkX(entity);
		}
		
	}


	public TodoResponseDTO getTodoOfDate(TodoRequestDTO dto) {
		log.info("서비스 도착");
		Todo entity = new Todo(dto, dto.getClickDate());
		Todo result = mapper.getTodoOfDate(entity);
		
		TodoResponseDTO resDto = TodoResponseDTO.builder()
				.todoNo(result.getTodoNo())
				.userId(result.getUserId())
				.todoContent(result.getTodoContent())
				.chkBtn(result.getChkBtn())
				.build();
		System.out.println("resDto는 : "+resDto);
		return resDto;
	}
	
	// 매개값으로 받은 board_bno의 사용자의 프로필컬러 hex 값을 받아오는 서비스
//	public String[] getProfColor(Integer[] boardBnos) {
//		
//		String
//		
//		for (int bno : boardBnos) {
//			mapper.getProfColor(bno);
//		}
//		return 
//	}
	
	
}
