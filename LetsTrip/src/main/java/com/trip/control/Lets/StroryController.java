package com.trip.control.Lets;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties.Problemdetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.dto.StoryDetailDto;
import com.trip.dto.Lets.BoardFormDto;
import com.trip.dto.Lets.StoryCardDto;
import com.trip.dto.Lets.StoryCommentDto;
import com.trip.dto.Lets.StoryMineDto;
import com.trip.service.Lets.StoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StroryController  {
			//자동주입
			@Autowired
			private StoryService storyService;
			
		   // 메인 
			@GetMapping("/letsmain")
			public String letsmain(Model model) {
						
					model.addAttribute("storyImgList" , storyService.getStoryImgUrls());
				
					return "story/main";
			}
		
			//스토리 작성
			@GetMapping("/weUpload")
			public String showUploadForm(Model model) {
			    model.addAttribute("boardFormDto", new BoardFormDto());
			    return "story/weUpload"; // templates/story/weUplod.html
			}

			@PostMapping("/weUpload")
			public String submitStory(@Valid @ModelAttribute BoardFormDto boardFormDto,
			                          BindingResult bindingResult,
			                          Principal principal,
			                          Model model) {

			    if (bindingResult.hasErrors()) {
			        return "story/weUpload";
			    }

			    storyService.saveStory(boardFormDto, principal.getName());
			    return "redirect:/myStory";
			}

			
			
			//스토리 보기
			@GetMapping("/weStory")
			public String weShow( Principal principal, Model model) {
				String loginId=null;
				if(principal !=null) loginId=principal.getName();
				model.addAttribute("cardList",storyService.getStoryList(loginId) );
				model.addAttribute("storyCommentDto", new StoryCommentDto());
				
					return "story/weStory";
			}
			
			// 나의 스토리 보기 (로그인한 사용자만)
		    @GetMapping("/myStory")
		    public String myStory(Model model,Principal principal) {
		        
		        List<StoryMineDto> myStories = storyService.getStoriesByUser(principal.getName()); // 해당 사용자의 스토리 가져오기
		        model.addAttribute("myStoryList", myStories);
		        model.addAttribute("sortType", "myStory");
		        return "story/myStory";
		    }
			
			@PostMapping("/comment")
			public String comment(StoryCommentDto storyCommentDto,@RequestParam("storyId") Long storyId, Principal principal) {
				if(principal !=null) {
					storyService.commentSave(storyCommentDto, storyId,principal.getName());
				}
				return "redirect:/weStory";
			}
			
			//스토리 상세보기
			@GetMapping("/StoryDetail")
			public String getStoryDetail(@RequestParam("id") Long id, Model model) {
			   
			    model.addAttribute("story", storyService.getStoryDetail(id));
			    return "story/StoryDetail"; // templates/story/StoryDetail.html
			}

			
			
			
				
}
