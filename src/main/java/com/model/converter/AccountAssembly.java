package com.model.converter;

import com.model.dto.AccountFormDTO;
import com.model.entity.Account;
import com.util.JwtUtil;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-18 17:42
 */
public class AccountAssembly {

    public static Account toDomain(AccountFormDTO accountFormDTO) {
        Account account = new Account();
        account.setUserId(JwtUtil.getUserId());
        return toDomain(account, accountFormDTO);
    }

    public static Account toDomain(Account account, AccountFormDTO accountFormDTO) {
        account.setAccessKeyId(accountFormDTO.getAccessKeyId());
        account.setAccessKeySecret(accountFormDTO.getAccessKeySecret());
        return account;
    }

}
