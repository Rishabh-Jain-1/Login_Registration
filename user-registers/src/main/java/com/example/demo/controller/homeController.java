package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Bean.UserBean;
import com.example.demo.Repo.UsersRepo;

@Controller
public class homeController {
	@Autowired
	UsersRepo usersrepo;

	@RequestMapping({ "home", "/" })
	public String home() {
		return "home";
	}

	@RequestMapping("signup")
	public String signup(Model m) {
		m.addAttribute("userbean", new UserBean());
		return "signup";
	}

	@RequestMapping("submitForm")
	public String validateForm(@Valid @ModelAttribute("userbean") UserBean userbean, BindingResult bindingresult,
			Model m) {

		if (bindingresult.hasErrors()) {
			m.addAttribute("error", "Please Insert correct Details");
			return "signup";
		}

		usersrepo.save(userbean);
		m.addAttribute("success", "Registration Successful!");
		return "login";
	}

	@RequestMapping("/login")
	public String login(Model m) {
		m.addAttribute("userbean", new UserBean());
		return "login";
	}

	@RequestMapping("validateUser")
	public String validate(@ModelAttribute("userbean") UserBean userbean, Model m, HttpSession session) {
		UserBean userDB = usersrepo.findByEmail(userbean.getEmail());
		if (userDB != null) {
			if (userDB.getPassword().equals(userbean.getPassword())) {
				session.setAttribute("user", userbean.getEmail());
				session.setMaxInactiveInterval(60);
				m.addAttribute("email", userbean.getEmail());
				return "Dummy";
			} else {
				m.addAttribute("bad", "Bad Credentials!");
				return "login";
			}
		} else {
			m.addAttribute("bad", "User Not Found!");
			return "login";
		}

	}
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest req,Model m) {
		req.getSession().invalidate();
		m.addAttribute("success","Logout Successful");
		
		return "home";
	}
	@RequestMapping("dummy")
	public String getUser(HttpSession session, Model m) {
		String user= (String) session.getAttribute("user");
		try {
		if (user != null) {
			return "Dummy";
		} else {
			return "sessionExpired";
		}
	}
	
	catch(IllegalStateException exception) {
		return "sessionExpired";
	}
	}
	

}
