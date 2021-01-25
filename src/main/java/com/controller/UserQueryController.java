package com.controller;

import com.enums.ResponseEnum;
import com.model.dto.QueryDTO;
import com.model.vo.PageVO;
import com.model.vo.ResponseVO;
import com.model.vo.UserVO;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019-12-04 10:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "用户模块")
@RequestMapping("/user")
public class UserQueryController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("查询用户信息")
    @GetMapping("/info")
    public ResponseVO<UserVO> getUserInfo() {
        UserVO userVO = userService.getUserInfo();
        return new ResponseVO<>(ResponseEnum.SUCCESS, userVO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation("分页查询用户信息列表")
    @PostMapping("/info/paging")
    public ResponseVO<PageVO<UserVO>> pagingUserInfo(@ApiParam(name = "queryDTO", value = "分页查询请求模型", required = true)
                                                     @Validated @RequestBody QueryDTO queryDTO) {
        PageVO<UserVO> userPageVO = userService.pagingUserInfo(queryDTO);
        return new ResponseVO<>(ResponseEnum.SUCCESS, userPageVO);
    }

}
