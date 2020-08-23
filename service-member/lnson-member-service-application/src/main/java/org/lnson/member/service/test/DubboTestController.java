package org.lnson.member.service.test;

import org.lnson.member.service.MemberService;
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
    public DubboTestController(MemberService memberService) {
        this.memberService = memberService;
    }

    private MemberService memberService;

    @GetMapping("/test/member")
    public BaseResult testMember() {
        return BaseResponse.success(ReturnEnum.SUCCESS, memberService.list());
    }

}
