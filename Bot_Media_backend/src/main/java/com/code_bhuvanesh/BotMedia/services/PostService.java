package com.code_bhuvanesh.BotMedia.services;

import com.code_bhuvanesh.BotMedia.Model.Post;
import com.code_bhuvanesh.BotMedia.Model.User;
import com.code_bhuvanesh.BotMedia.Repository.PostRepository;
import com.code_bhuvanesh.BotMedia.Repository.UserRepository;
import com.code_bhuvanesh.BotMedia.Respones.PostGetResponse;
import com.code_bhuvanesh.BotMedia.Utils.Generators;
import com.code_bhuvanesh.BotMedia.dtos.PostUploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final String postDir = "uploads/";

    public Post savePost(PostUploadDto postUploadDto, String token){
        try {
            String postContent = postUploadDto.getPostContent();
            MultipartFile postImage = postUploadDto.getPostImage();

            System.out.println("post content : " + postContent );
            if(postContent == null || postContent.trim().isEmpty()){
                System.out.println("post content null");
                return null;
            }

            Post post = new Post();
            post.setPostContent(postContent);

            String username = jwtService.extractUsername(token);
            if(userRepository.findByUsername(username).isPresent()){
                User user = userRepository.findByUsername(username).get();
                post.setUserid(user);
            }
            else{
                System.out.println("token error while parsing token");
                return null;
            }

            if(postImage != null){
                File directory = new File(postDir);
                if(!directory.exists()) {
                    directory.mkdirs();
                }

//                Path filePath = Paths.get(postDir + postImage.getOriginalFilename());
//                Path filePath = Paths.get(postDir + username+"_"+postImage.getOriginalFilename());
                String filename = Generators.generateRandomFileName(username, postImage.getOriginalFilename());
                Path filePath = Paths.get(postDir + filename);
                Files.write(filePath, postImage.getBytes());

                post.setPostFileName(filename);
            }

            postRepository.save(post);
            return post;

        }
        catch (IOException exception){
            System.out.println("error saving post!!!");
            exception.printStackTrace();
        }

        return null;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
}
