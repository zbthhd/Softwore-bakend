package com.example.management_platform.mapper;


import com.example.management_platform.entity.StudentScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper
public interface StudentScoreMapper{
    void deleteByClassId(Integer classId);

    List<StudentScore> selectByClassId(Integer classId);
}
