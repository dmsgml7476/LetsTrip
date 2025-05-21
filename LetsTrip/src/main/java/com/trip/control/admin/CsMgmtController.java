package com.trip.control.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.trip.dto.admin.CsMgmtDto;
import com.trip.entity.Member.CustomerServiceEntity;
import com.trip.constant.Member.Status;
import com.trip.repository.Member.CustomerServiceRepository;
import com.trip.service.admin.CustomerServiceAnswerService;

@Controller
@RequestMapping("/admin")
public class CsMgmtController {
	
	@Autowired
	private CustomerServiceRepository customerServiceRepository;
	
	@Autowired
	private CustomerServiceAnswerService customerServiceAnswerService;
	
	
	@GetMapping("/csMgmt")
	public String csMgmtPage(@RequestParam(name = "loginId", required=false) String loginId, 
							@RequestParam(name="page", defaultValue="0")int page,
							Model model) {
		
		int pageSize = 10;
	    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("questionTime").descending());

	    Page<CustomerServiceEntity> csPage;

	    if (loginId != null && !loginId.isBlank()) {
	        csPage = customerServiceRepository.findByUser_LoginIdContainingIgnoreCase(loginId, pageable);
	        model.addAttribute("loginId", loginId);
	    } else {
	        csPage = customerServiceRepository.findAll(pageable);
	    }

	    List<CsMgmtDto> csList = csPage.getContent().stream()
	            .map(CsMgmtDto::from)
	            .collect(Collectors.toList());

	    int totalPages = csPage.getTotalPages();

	    model.addAttribute("csList", csList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);

	    return "admin/page/csMgmt";
	}
	
	@PostMapping("/csMgmt/answer")
	public String answerSubmit(@RequestParam("status")  String status,
								@RequestParam(value = "answerText", required = false) String answerText,
								@RequestParam("csId") Long csId) {
		if ("IN_PROGRESS".equals(status)) {
		    // 답변 없이 상태만 업데이트
			customerServiceAnswerService.updateStatus(csId, Status.IN_PROGRESS);
		} else {
		    customerServiceAnswerService.saveAnswer(csId, status, answerText);
		}
		
		return "redirect:/admin/csMgmt";
	}
	

	

}
