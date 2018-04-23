package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoController {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@GetMapping(value="/")
	public ModelAndView renderIndex() 
	{
		ModelAndView mv= new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@GetMapping(value="/facebook")
	public ModelAndView renderFB() 
	{
		ModelAndView mv= new ModelAndView();
		mv.setViewName("facebook");
		return mv;
	}
	
    @PostMapping(value="student/add")
    public ModelAndView saveStudent(
    	@RequestParam(name="name",required=true)String name, @RequestParam String email)
    	{
    		Student s=new Student();
    		s.setName(name);
    		s.setEmail(email);
    		studentRepo.save(s);
    		return new ModelAndView("redirect:/students");
    	}
    @GetMapping(value="/students")
    public ModelAndView getAllStudents()
    {
    	ModelAndView mv= new ModelAndView();
    	List<Student>students=studentRepo.findAll();
    	mv.addObject("students",students);
    	mv.setViewName("allstudents");
    	return mv;
    }
    
    @GetMapping(value="/student")
    public ModelAndView getOneStudent (@RequestParam(name="id",required=true)String id)
    {
    	ModelAndView mv= new ModelAndView();
    	try 
    	{
    		Optional<Student>result=studentRepo.findById(Integer.parseInt(id));
    		if(result.isPresent())
    		{
    			Student s=result.get();
    			mv.addObject("student",s);
    			mv.setViewName("studentInfo");
    		}
    		else 
    		{
    			throw new Exception("lol");
    		}
    	}
    	catch (Exception e)
    	{
    		mv.addObject("error","student not present");
    		mv.setViewName("studenterror");
    		e.printStackTrace();
    	}
    	return mv;
    }
    @PostMapping(value="/facebookRedirect")
    public ModelAndView handleRedirect(
    	@RequestParam(name="myId")String myId, 
    	@RequestParam (name="myName")String myName,
    	@RequestParam(name="myFriends")String myFriends,
    	@RequestParam(name="myEmail")String myEmail,
    	HttpServletRequest req)
    	{
    		System.out.println(myId+myName+myFriends+myEmail);
    		String[]splitted=myFriends.split("/");
    		for (int i=0;i<splitted.length;i++)
    		{
    			System.out.println(i+":"+splitted[i]);
    		}
    		return new ModelAndView("allStudents");
    	}
}
