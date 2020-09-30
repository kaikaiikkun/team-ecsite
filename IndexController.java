package jp.co.internous.mushrooms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.mushrooms.model.domain.MstCategory;
import jp.co.internous.mushrooms.model.domain.MstProduct;
import jp.co.internous.mushrooms.model.form.SearchForm;
import jp.co.internous.mushrooms.model.mapper.MstCategoryMapper;
import jp.co.internous.mushrooms.model.mapper.MstProductMapper;
import jp.co.internous.mushrooms.model.session.LoginSession;

@Controller
@RequestMapping("/mushrooms")
public class IndexController {

	@Autowired
	private MstCategoryMapper mstCategoryMapper;
	
	@Autowired
	private MstProductMapper mstProductMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@RequestMapping("/")
	public String index(Model m) {
		
		if (!loginSession.getLogined() && loginSession.getTmpUserId() == 0) {
			int tmpUserId = (int)(Math.random() * 1000000000 * -1);
			while (tmpUserId > -100000000) {
				tmpUserId *= 10;
			}
			loginSession.setTmpUserId(tmpUserId);
		}

		List<MstCategory> category = mstCategoryMapper.find();
		
		List<MstProduct> products = mstProductMapper.find();
		
		if (products != null && products.size() > 0) {
			m.addAttribute("products", products);
			m.addAttribute("category", category);
			m.addAttribute("selected", 0);
			m.addAttribute("loginSession",loginSession);
		}
		return "index";
	} 
	
	@RequestMapping("/searchItem")
	public String searchItem(SearchForm s, Model m) {
		
		List<MstProduct> products;
		String keywords = s.getKeywords().replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		
		if (s.getCategory() == 0) {
			// 
			products = mstProductMapper.findByProductName(keywords.split(" "));
		} else {
			products = mstProductMapper.findByCategoryAndProductName(s.getCategory(), keywords.split(" "));
			
		}
		
		List<MstCategory> category = mstCategoryMapper.find();
		m.addAttribute("keywords", keywords);
		m.addAttribute("selected", s.getCategory()); 
		m.addAttribute("category", category); 
		m.addAttribute("products", products);
		m.addAttribute("loginSession",loginSession);
		return "index";
		
//		自分のコード
//		if( category == 0 && itemName.isEmpty() ) {
//			List<MstProduct> products = mstProductMapper.find();
//			if (products != null && products.size() > 0) {
//				m.addAttribute("products", products);
//			}
//		}else if( category == 0 ) {
//			List<MstProduct> products = mstProductMapper.findByProductName();
//			if (products != null && products.size() > 0) {
//				m.addAttribute("products", products);
//			}
//		}else {
//			List<MstProduct> products = mstProductMapper.findByCategoryAndProductName();
//			if (products != null && products.size() > 0) {
//				m.addAttribute("products", products);
//			}
//		}
	}
	
}
