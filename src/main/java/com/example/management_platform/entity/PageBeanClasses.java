package com.example.management_platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBeanClasses {
    private List<ClassInfo> list;
    long total;
}
