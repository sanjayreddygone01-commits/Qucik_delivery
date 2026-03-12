import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam String phone, @RequestParam String orderId, @RequestParam String reason) {
        orderService.cancelOrder(phone, orderId, reason);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}