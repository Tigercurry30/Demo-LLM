package com.example.service.impl;

import com.example.entity.po.School;
import com.example.mapper.SchoolMapper;
import com.example.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 校区表 服务实现类
 * </p>
 *
 * @author tigercurry
 * @since 2025-04-13
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

}
