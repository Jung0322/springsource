package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.domain.BookDTO;
import com.company.service.BookService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/book/*")
@Log4j2
public class BookRestController {
	
	@Autowired
	private BookService service;
	//새 도서 입력
		@PostMapping("/insert-rest")
		//@ResponseBody// 일반 controller에서 return을 jsp가 아닌 값으로 return 
		public String insertPost(@RequestBody BookDTO bookDTO) {
			log.info("insert 요청");
			try {
				if (service.insert(bookDTO)) {
					return "success";
				}
			} catch (Exception e) {
				return"fail";
			}
			return "fail";
		}
		
		@GetMapping(value="/{code}", produces = MediaType.APPLICATION_JSON_VALUE)	//path에 들어오는 값이 pathvariable에 그대로 사용
		public ResponseEntity<BookDTO> row(@PathVariable("code") String code){
			log.info("책 하나 가져오기"+code);
			
			BookDTO dto = service.getRow(code);
			
			return new ResponseEntity<BookDTO>(dto,HttpStatus.OK);
		}
		
		@GetMapping(value="/rest-list", produces =  MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<BookDTO>> getList(){
			log.info("rest 방식 list 요청");
			return new  ResponseEntity<List<BookDTO>>(service.list(),HttpStatus.OK);
		}
		
		
		@DeleteMapping("/{code}")
		public ResponseEntity<String> delete(@PathVariable("code") String code){
			
			log.info("rest 삭제 " + code);
			
			if(service.delete(code)) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			}
			
		}
		
		
		@PutMapping("/update")
		public ResponseEntity<String> modify(@RequestBody BookDTO upBookDTO){
			log.info("rest 수정" + upBookDTO);
			
			if(service.update(upBookDTO)) {
				return new ResponseEntity<String>("success",HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
			}
		}
}
