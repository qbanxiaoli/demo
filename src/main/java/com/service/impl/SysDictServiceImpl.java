package com.service.impl;

import com.model.entity.QSysDict;
import com.dao.mapper.SysDictMapper;
import com.dao.querydsl.SysDictDao;
import com.dao.repository.SysDictRepository;
import com.enums.ResponseEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.model.converter.SysDictAssembly;
import com.model.dto.SysDictDTO;
import com.model.dto.SysDictFromDTO;
import com.model.dto.SysDictQueryDTO;
import com.model.entity.SysDict;
import com.model.vo.PageVO;
import com.model.vo.SysDictVO;
import com.service.SysDictService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author qbanxiaoli
 * @description
 * @create 2020-05-15 11:44
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl implements SysDictService {

    private final SysDictDao sysDictDao;

    private final SysDictMapper sysDictMapper;

    private final SysDictRepository sysDictRepository;

    @Override
    public List<SysDictVO> getSysDictList(SysDictDTO sysDictDTO) {
        return sysDictDao.getSysDictList(sysDictDTO);
    }

    @Override
    public List<SysDictVO> getAllSysDictList(SysDictDTO sysDictDTO) {
        return sysDictDao.getAllSysDictList(sysDictDTO);
    }

    @Override
    public PageVO<SysDict> pagingSysDict(SysDictQueryDTO sysDictQueryDTO) {
        // 使用PageHelper分页插件进行分页查询，只对下一条的查询结果进行分页
        Page<SysDict> page = PageHelper.startPage(sysDictQueryDTO.getCurrentPage(), sysDictQueryDTO.getPageSize());
        // 动态查询条件拼装
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        // 文件名关键字查询
        if (StringUtils.isNotBlank(sysDictQueryDTO.getKeyword())) {
            criteria.andLike(QSysDict.sysDict.description.getMetadata().getName(), sysDictQueryDTO.getKeyword() + "%");
        }
        // 开始时间
        if (sysDictQueryDTO.getStartTime() != null) {
            criteria.andGreaterThanOrEqualTo(QSysDict.sysDict.gmtCreated.getMetadata().getName(), sysDictQueryDTO.getStartTime());
        }
        // 结束时间
        if (sysDictQueryDTO.getEndTime() != null) {
            criteria.andLessThanOrEqualTo(QSysDict.sysDict.gmtCreated.getMetadata().getName(), sysDictQueryDTO.getEndTime());
        }
        // 排序规则
        if (sysDictQueryDTO.getOrder()) {
            example.orderBy(QSysDict.sysDict.gmtCreated.getMetadata().getName()).desc();
        }
        // 分页查询数据字典列表
        List<SysDict> sysDictList = sysDictMapper.selectByExample(example);
        return PageVO.<SysDict>builder()
                .currentPage(sysDictQueryDTO.getCurrentPage())
                .pageSize(sysDictQueryDTO.getPageSize())
                .total(page.getTotal())
                .results(sysDictList)
                .build();
    }

    @Override
    public void addSysDict(SysDictFromDTO sysDictFromDTO) {
        SysDict sysDict = sysDictRepository.findByTableNameAndFieldNameAndFieldValue(sysDictFromDTO.getTableName(), sysDictFromDTO.getFieldName(), sysDictFromDTO.getFieldValue());
        if (sysDict != null) {
            throw new RuntimeException(ResponseEnum.DATA_EXIST.name());
        }
        sysDict = SysDictAssembly.toDomain(sysDictFromDTO);
        sysDictRepository.save(sysDict);
    }

}
