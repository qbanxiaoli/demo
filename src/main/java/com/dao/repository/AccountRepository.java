package com.dao.repository;

import com.model.entity.Account;
import com.dao.BaseRepository;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-01-18 17:26
 */
public interface AccountRepository extends BaseRepository<Account, Long> {

    Account findByUserId(Long userId);

}
