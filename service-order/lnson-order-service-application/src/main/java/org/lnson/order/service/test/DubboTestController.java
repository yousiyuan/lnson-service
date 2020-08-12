package org.lnson.order.service.test;

import com.alibaba.dubbo.config.annotation.Reference;
import org.lnson.member.service.MemberService;
import org.lnson.order.service.OrderService;
import org.lnson.service.response.BaseResponse;
import org.lnson.service.response.BaseResult;
import org.lnson.service.response.ReturnEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class DubboTestController {

    @Reference(group = "member-group", version = "1.0.0")
    private MemberService memberService;

    @Reference(group = "order-group", version = "1.0.0")
    private OrderService orderService;

    @GetMapping("/test/member")
    public BaseResult testMember() {
        return BaseResponse.success(ReturnEnum.SUCCESS, memberService.list());
    }

    @GetMapping("/test/order")
    public BaseResult testOrder() {
        return BaseResponse.success(ReturnEnum.SUCCESS, orderService.get());
    }

}
