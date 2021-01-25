package com.service.impl;

import com.model.entity.QSysUser;
import com.aop.annotation.DataDictClass;
import com.aop.annotation.ImageClass;
import com.dao.mapper.SysUserMapper;
import com.dao.repository.SysRoleRepository;
import com.dao.repository.SysUserRepository;
import com.enums.AuthoritiesEnum;
import com.enums.ResponseEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.converter.UserAssembly;
import com.model.dto.QueryDTO;
import com.model.dto.RegisterFormDTO;
import com.model.entity.SysRole;
import com.model.entity.SysFileInfo;
import com.model.entity.SysUser;
import com.model.vo.PageVO;
import com.model.vo.UserVO;
import com.service.UserService;
import com.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author terminus
 * @description
 * @create 2019/12/11 11:38 上午
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final SysUserMapper sysUserMapper;

    private final SysUserRepository sysUserRepository;

    private final SysRoleRepository sysRoleRepository;

    @Override
    public void register(RegisterFormDTO registerFormDTO) {
        SysUser sysUser = sysUserRepository.findByUsername(registerFormDTO.getUsername());
        if (sysUser != null) {
            throw new RuntimeException(ResponseEnum.USERNAME_EXIST.name());
        }
        sysUser = new SysUser();
        sysUser.setUsername(registerFormDTO.getUsername());
        sysUser.setPassword(new BCryptPasswordEncoder().encode(registerFormDTO.getPassword()));

        // 添加权限
        List<SysRole> authorities = new ArrayList<>();
        SysRole sysRole = sysRoleRepository.findByAuthority(AuthoritiesEnum.USER.getRole());
        authorities.add(sysRole);

        // 设置权限列表
        sysUser.setAuthorities(authorities);
        sysUserRepository.save(sysUser);
    }

    @Override
    @ImageClass
    @DataDictClass
    public UserVO getUserInfo() {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(JwtUtil.getUserId());
        return UserAssembly.toDomain(sysUser);
    }

    @Override
    @ImageClass
    @DataDictClass
    public List<UserVO> getUserInfoList() {
        List<SysUser> sysUserList = sysUserMapper.selectAll();
        return UserAssembly.toDomain(sysUserList);
    }

    @Override
    @ImageClass
    @DataDictClass
    public PageVO<UserVO> pagingUserInfo(QueryDTO queryDTO) {
        // 使用PageHelper分页插件进行分页查询，只对下一条的查询结果进行分页
        Page<SysFileInfo> page = PageHelper.startPage(queryDTO.getCurrentPage(), queryDTO.getPageSize());
        // 动态查询条件拼装
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        // 文件名关键字查询
        if (StringUtils.isNotBlank(queryDTO.getKeyword())) {
            criteria.andLike(QSysUser.sysUser.username.getMetadata().getName(), queryDTO.getKeyword() + "%");
        }
        // 开始时间
        if (queryDTO.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo(QSysUser.sysUser.gmtCreated.getMetadata().getName(), queryDTO.getStartTime());
        }
        // 结束时间
        if (queryDTO.getEndTime() != null) {
            criteria.andLessThanOrEqualTo(QSysUser.sysUser.gmtCreated.getMetadata().getName(), queryDTO.getEndTime());
        }
        // 排序规则
        if (queryDTO.getOrder()) {
            example.orderBy(QSysUser.sysUser.gmtCreated.getMetadata().getName()).desc();
        }
        // 分页查询用户信息列表
        List<SysUser> sysUserList = sysUserMapper.selectByExample(example);
        // 数据转换
        List<UserVO> userVOList = UserAssembly.toDomain(sysUserList);
        return PageVO.<UserVO>builder()
                .currentPage(queryDTO.getCurrentPage())
                .pageSize(queryDTO.getPageSize())
                .total(page.getTotal())
                .results(userVOList)
                .build();
    }

}
