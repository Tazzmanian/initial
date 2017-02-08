package com.example.user;

import com.example.access.Views;
import com.example.exceptions.UserNotFoundException;
import com.example.security.UserAuthenticationResponse;
import com.example.security.UserTokenUtil;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private String tokenHeader = "Authorization";

    @Autowired
    private UserService service;

    @Autowired
    private UserTokenUtil userTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/user/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity create(@RequestBody User user) throws UnsupportedEncodingException {

        String token = userTokenUtil.generateToken(user);

        service.save(user, token);

        if (!userTokenUtil.validateToken(token, user)) {
            throw new UserNotFoundException("Could not validate token for this user!");
        }
        return ResponseEntity.ok(new UserAuthenticationResponse(token));
    }

    @PreAuthorize("hasAuthority('PERM_VIEW_USER')")
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUsers(Pageable pageRequest) {
        System.out.println(pageRequest.toString());
        return service.getAllUsers(pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('PERM_DELETE_USER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User remove(@PathVariable Long id) {
        try {
            return service.remove(id);
        } catch (Exception e) {
            throw new UserNotFoundException(id.toString());
        }
    }

    @PreAuthorize("hasAuthority('PERM_EDIT_USER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User update(@PathVariable Long id, @RequestBody User user) throws Exception {
        return service.update(id, user);
    }

    @PreAuthorize("hasAuthority('PERM_VIEW_USER')")
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/users/search/{firstName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> search(@PathVariable String firstName, Pageable pageRequest) throws Exception {
        return service.findByFirstName(firstName, pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/flagged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllFlaggedUsers(Pageable pageRequest) {
        return service.getAllFlaggedUsers(pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/unflagged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAllUnflaggedUsers(Pageable pageRequest) {
        return service.getAllUnflaggedUsers(pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/change/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User changeUserFlag(@PathVariable String name) {
        return service.changeFlag(name);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "user/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User enableUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = userTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null) {
            user.setEnabled(true);
        }
        return user;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "user/auth/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User enableUser(@PathVariable String token) {
        String username = userTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null) {
            user.setEnabled(true);
        }
        return user;
    }
}
