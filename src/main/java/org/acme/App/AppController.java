package org.acme.App;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.acme.Files.FileInfo;
import org.acme.Files.FileUploadService;
import org.acme.JWT.JwtService;
import org.acme.JWT.LoginResponse;
import org.acme.Users.User;
import org.acme.Users.UserService;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Path("")
public class AppController {

    @Inject
     FileUploadService fileUploadService;
    @Inject
    JwtService jwtService;


    @POST
    @RolesAllowed("user")
    @Path("/upload/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response fileUpload(@MultipartForm MultipartFormDataInput input) {


        return Response.ok().
                entity(fileUploadService.uploadFile(input)).build();
    }
//Get all files
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileInfo> getAllFiles() {
        return fileUploadService.getAllFiles();
    }


    //User
    @Inject
    UserService userService;

    @POST
    @Consumes("application/json")
    @Path("/register")
    public Response registerUser(@Valid User user) {
        // Kiểm tra xem tên người dùng đã tồn tại chưa
        if (userService.findUserByUsername(user.username).isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Username already exists").build();
        }

        // Lưu thông tin người dùng vào cơ sở dữ liệu
        userService.createUser(user);

        return Response.ok().entity("User registered successfully").build();
    }

    @POST

    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login")
    public Response login(User loginDto) {
        // Validate user credentials
        if (userService.isValidUser(loginDto.getUsername(), loginDto.getPassword())) {
            // Generate and return a JWT
            String token = jwtService.generateJwt(loginDto.getUsername());
            return Response.ok(new LoginResponse(token)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}