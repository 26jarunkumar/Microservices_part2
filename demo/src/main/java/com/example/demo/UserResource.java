package com.example.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.repository.UserDaoService;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource getUser(@PathVariable int id){
		User usr = userService.findUser(id); 
		if (null == usr)
			throw new UserNotFoundException("User ID "+id);
		Resource<User> resource = new Resource<User>(usr);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/users")
	public Resource<User> saveUser(@Valid @RequestBody User user){
		User usr = userService.save(user);
		/*
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
		 return ResponseEntity.created(location).build();
		 */
			Resource<User> resource = new Resource<User>(usr);
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(usr.getId()));
			resource.add(linkTo.withRel("saved-user"));
			return resource;
		
	}
	
	@DeleteMapping("/users/{id}")
	public Resource  deleteUser(@PathVariable int id){
		System.out.println(" Delete ID "+id);
		User usr = userService.deleteUser(id);
		System.out.println(" deelete usr "+usr);
		if (null == usr)
			throw new UserNotFoundException("User ID "+id);
		Resource<User> resource = new Resource<User>(usr);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
}
