package pl.pjwstk.s17651.mvcdemo.codility.external;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrdersService {

    public List<Item> itemsBought(String username) {
        return new ArrayList<>(Arrays.asList(new Item("A", 1), new Item("B", 2)));
    }
}
