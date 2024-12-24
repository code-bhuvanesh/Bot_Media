package com.code_bhuvanesh.BotMedia.controllers;


import com.code_bhuvanesh.BotMedia.Model.Post;
import com.code_bhuvanesh.BotMedia.Model.User;
import com.code_bhuvanesh.BotMedia.Repository.UserRepository;
import com.code_bhuvanesh.BotMedia.Respones.PostGetResponse;
import com.code_bhuvanesh.BotMedia.Respones.PostResponse;
import com.code_bhuvanesh.BotMedia.Respones.PostUploadResponse;
import com.code_bhuvanesh.BotMedia.Respones.ResponseType;
import com.code_bhuvanesh.BotMedia.dtos.PostUploadDto;
import com.code_bhuvanesh.BotMedia.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Files;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/post")
@RestController
public class PostController {


    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/upload")
    public ResponseEntity<PostUploadResponse> uploadPost(@ModelAttribute PostUploadDto postUploadDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Post post = postService.savePost(postUploadDto, token);
        if(post == null){
            System.out.println("post is null");
            return ResponseEntity.badRequest().body(new PostUploadResponse().setResponse(ResponseType.ERROR));
        }
        PostUploadResponse postUploadResponse = new PostUploadResponse().setResponse(ResponseType.SUCCESS).setPostId(post.getId());
        return ResponseEntity.ok(postUploadResponse);

    }

    @GetMapping("/")
    public ResponseEntity<PostGetResponse> getAllPosts(HttpServletRequest request){
        List<Post> posts = postService.getAllPosts();
        List<PostResponse> postResponses = new ArrayList<>();
        String currentUrl = request.getRequestURL().toString();
        System.out.println("current url : " + currentUrl);
        for(Post p : posts){
            PostResponse newPostResponse = new PostResponse();
            newPostResponse.setId(p.getId());
            newPostResponse.setPostContent(p.getPostContent());
            newPostResponse.setFileUrl(currentUrl + "image/" + p.getPostFileName());
            System.out.println(p.getUser());
            User postPublisher = p.getUser();
            newPostResponse.setUserid(p.getId());
            newPostResponse.setUsername(postPublisher.getUsername());

            postResponses.add(newPostResponse);
        }

        return ResponseEntity.ok(new PostGetResponse(postResponses));
    }


    @GetMapping("image/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String filename) {
        try {
            // Set the path to the uploaded file
            Path filePath = Paths.get("uploads/" + filename);
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                // Dynamically determine the content type (MIME type) of the file
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";  // Default to binary if content type is unknown
                }

                // Return the image with the appropriate content type and inline disposition
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))  // Dynamically set content type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")  // Show inline in browser
                        .body(resource);  // Serve the image content
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if the file is not found
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Return 500 if there's an error
        }
    }

}
