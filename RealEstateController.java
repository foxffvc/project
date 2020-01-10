package cn.reiq.conttroller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.reiq.pojo.RealEstate;
import cn.reiq.service.RealEstateService;
import cn.reiq.tools.PageSupport;

@Controller
public class RealEstateController {
	@Resource
	private RealEstateService reService;

	@RequestMapping("relist.html")
	public String selectREList(Integer pageIndex, Integer type,
			String typeName, Model model) {
		if (pageIndex==null||pageIndex<1) {
			pageIndex=1;
		}
		PageSupport page=new PageSupport();
		page.setPageSize(5);
		List<RealEstate> list=reService.getRealEstateByType(type, typeName, null,null);
		int totalCount=0;
		if (list!=null) {
			totalCount=list.size();
		}
		page.setTotalCount(totalCount);
		if (pageIndex>page.getTotalPageCount()) {
			pageIndex=page.getTotalPageCount();
		}
		page.setCurrentPageNo(pageIndex);
		
		List<RealEstate> reList=reService.getRealEstateByType(type, typeName, page.getCurrentPageNo(), page.getPageSize());
		
		model.addAttribute("reList", reList);
		model.addAttribute("type", type);
		model.addAttribute("typeName", typeName);
		model.addAttribute("currentPageNo", page.getCurrentPageNo());
		model.addAttribute("totalCount", page.getTotalCount());
		model.addAttribute("totalPageCount", page.getTotalPageCount());
		return "realestatelist";
	}
}
