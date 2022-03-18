package pl.pjwstk.s17651.mvcdemo.codility.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pjwstk.s17651.mvcdemo.codility.external.OrdersService;

@Service
public class UsersService {

    private OrdersService ordersService;

    @Autowired
    public UsersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    public int getNumberOfItemsBought(String username) {
        return ordersService.itemsBought(username).size();
    }
}
