package org.lnson.order.service.test;

import org.lnson.order.service.OrderService;
import org.lnson.service.response.BaseResponse;
import org.lnson.service.response.BaseResult;
import org.lnson.service.response.ReturnEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubboTestController {

    @Autowired
    public DubboTestController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;

    @GetMapping("/test/order")
    public BaseResult testOrder() {
        return BaseResponse.success(ReturnEnum.SUCCESS, orderService.get());
    }

}
