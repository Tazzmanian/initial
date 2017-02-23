package com.example.user;

import com.example.exceptions.UserNotFoundException;
import com.example.security.UserAuthenticationResponse;
import com.example.security.UserTokenUtil;
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
    @RequestMapping(value = "/users/privileged", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public User saveAdmin(@RequestBody User user) {
        return service.saveAdmin(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/normal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDTO> getAllUsers(Pageable pageRequest) {
        return service.getAllUsers(pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('PERM_DELETE_USER')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User remove(@PathVariable Long id) {
        try {
            return service.remove(id);
        } catch (Exception e) {
            throw new UserNotFoundException(id.toString());
        }
    }

    @PreAuthorize("hasAuthority('PERM_EDIT_USER')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User update(@PathVariable Long id, @RequestBody User user) throws Exception {
        return service.update(id, user);
    }

    @PreAuthorize("hasAuthority('PERM_VIEW_USER')")
    @RequestMapping(value = "/users/search/{firstName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDTO> search(@PathVariable String firstName, Pageable pageRequest) throws Exception {
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
    @RequestMapping(value = "/users/active/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public User activate(@PathVariable Long id) {
        return service.changeActive(id);
    }

    @RequestMapping(value = "users/authorization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO enableUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = userTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null) {
            user.setEnabled(true);
        }
        UserDTO dto = UserMapper.mapEntityIntoDTO(user);
        return dto;
    }

    @RequestMapping(value = "users/authorization/{token}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO enableUser(@PathVariable String token) {
        String username = userTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if (user != null) {
            user.setEnabled(true);
        }
        UserDTO dto = UserMapper.mapEntityIntoDTO(user);
        return dto;
    }
}
