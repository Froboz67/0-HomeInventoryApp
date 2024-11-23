package com.techelevator.controller;


import com.amazonaws.auth.policy.Resource;
import com.techelevator.dao.PhotoDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Photo;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@CrossOrigin (origins = "http://127.0.0.1:5173")
@PreAuthorize("isAuthenticated()")
//@RequestMapping("/photo")
public class PhotoController {


    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private UserDao userDao;

    public PhotoController(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @PostMapping("/photo")
    public ResponseEntity<Photo> savePhoto(@RequestBody Photo photo, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        photoDao.savePhoto(photo, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(photo);
    }
    /*
    Method takes an uploaded file from the front end and saves it to a local folder.The storage folder has to be located outside the
    application root.
     */
    @PostMapping("/update/{itemId}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable int itemId, @RequestBody Photo photo, Principal principal) {
        System.out.println("inside update method");
        System.out.println(photo);
        User user = userDao.getUserByUsername(principal.getName());
        photoDao.updatePhoto(photo, photo.getItemId());
        return ResponseEntity.status(HttpStatus.OK).body(photo);
    }
    @PostMapping("/photo/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("itemId") int itemId, @RequestParam("file")MultipartFile file) {
        String folderPath = "D:/Kevin_Docs/Engel_Docs/Tech_Elevator/workspace/GitHub/HomeInventoryApp/item-photos";
        try {
            String fileName = file.getOriginalFilename();
            File destinationFile = new File(folderPath + File.separator + fileName);
            file.transferTo(destinationFile);

            String fileUrl = folderPath + fileName;

            System.out.println("File uploaded successfully " + fileUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file not saved " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("file upload failed");
        }
    }
    @GetMapping("/photo/{itemId}")
    public Photo getPhoto(@PathVariable int itemId) {
        return photoDao.getPhoto(itemId);
    }
    @GetMapping("/photo/file/item/{itemId}")
    public ResponseEntity<UrlResource> getPhotoFileByItmeId(@PathVariable int itemId) {
        try {
            Photo photo = photoDao.getPhoto(itemId);

            if (photo == null || photo.getName() == null) {
                System.out.println("photo or filename was null");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            String fileName = photo.getName();
            String photoUrl = photo.getPhotoUrl();

            Path filePath = Paths.get(photoUrl).resolve(fileName).normalize();

            UrlResource urlResource;
            try {
                urlResource = new UrlResource(filePath.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(null);
            }

            if (urlResource.exists()) {
                System.out.println("filename is " + photo.getName());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + urlResource.getFilename() + "\"")
                        .body(urlResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Photo> deletePhoto(@PathVariable int itemId, Photo photo, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        System.out.println("photo before deletion " + photo);
        photoDao.deletePhoto(photo, itemId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}
