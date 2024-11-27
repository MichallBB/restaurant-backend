package pos.restaurant.controller;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pos.restaurant.DTO.Order.DishInOrderDto;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendCookeddDishId(Long id) {
        simpMessagingTemplate.convertAndSend("/topic/cooked", id);
    }

    public void sendServedDishId(Long id) {
        simpMessagingTemplate.convertAndSend("/topic/served", id);
    }
}