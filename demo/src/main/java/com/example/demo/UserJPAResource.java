package com.example.demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.domain.Posts;
import com.example.demo.domain.User;
import com.example.demo.repository.PostsRepository;
import com.example.demo.repository.UserDaoService;
import com.example.demo.repository.UserRepository;

@RestController
public class UserJPAResource {

	@Autowired
	private UserDaoService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostsRepository postRepository;
	
	
	@GetMapping("/jpa/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public Resource getUser(@PathVariable int id){
		
		Optional<User> usr = userRepository.findById(id); 
		if (!usr.isPresent())
			throw new UserNotFoundException("User ID "+id);
		Resource<User> resource = new Resource<User>(usr.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/jpa/users")
	public Resource<User> saveUser(@Valid @RequestBody User user){
		
		User usr=userRepository.save(user);
		/*
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
		 return ResponseEntity.created(location).build();
		 */
			Resource<User> resource = new Resource<User>(usr);
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(usr.getId()));
			resource.add(linkTo.withRel("saved-user"));
			return resource;
		
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public Resource  deleteUser(@PathVariable int id){
		System.out.println(" Delete ID "+id);
		
		userRepository.deleteById(id);
		User usr = userService.deleteUser(id);
		System.out.println(" deelete usr "+usr);
		if (null == usr)
			throw new UserNotFoundException("User ID "+id);
		Resource<User> resource = new Resource<User>(usr);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(usr.getId()));
		resource.add(linkTo.withRel("deleted-user"));
		return resource;
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Posts> getUserPosts(@PathVariable int id){
		
		Optional<User> usr = userRepository.findById(id); 
		if (!usr.isPresent())
			throw new UserNotFoundException("User ID "+id);
		
		return usr.get().getPost();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public Resource<Posts> createPosts(@PathVariable int id, @RequestBody Posts post){
		
		Optional<User> usr = userRepository.findById(id); 
		if (!usr.isPresent())
			throw new UserNotFoundException("User ID "+id);
		
		post.setUser(usr.get());
		
		Posts pst = postRepository.save(post);
		
		/*
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usr.getId()).toUri();
		 return ResponseEntity.created(location).build();
		 */
			Resource<Posts> resource = new Resource<Posts>(pst);
			ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserPosts((usr.get().getId())));
			resource.add(linkTo.withRel("saved-post"));
			return resource;
		
	}
	
}
	