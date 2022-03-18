package pl.pjwstk.s17651.mvcdemo.codility.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(value = "/{username}/items/total", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Integer> totalItemsBought(@PathVariable("username") String userName) {
        int numberOfItemsBought = usersService.getNumberOfItemsBought(userName);

        return Collections.singletonMap("totalItemsBought", numberOfItemsBought);
    }
}
