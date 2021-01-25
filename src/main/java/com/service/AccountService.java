package com.service;

import com.model.dto.AccountFormDTO;
import com.model.entity.Account;
import com.model.vo.AccountVO;
import com.model.vo.ResultVO;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2019/1/28 3:41 PM
 */
public interface AccountService {

    // 查询火币accessKeyId和accessKeySecret
    Account getAccount();

    // 查询账户信息
    ResultVO<List<AccountVO>> getAccountInformation();

    // 添加火币accessKeyId和accessKeySecret
    Long addAccount(AccountFormDTO accountFormDTO);

    // 更新火币accessKeyId和accessKeySecret
    Long updateAccount(AccountFormDTO accountFormDTO);

}
