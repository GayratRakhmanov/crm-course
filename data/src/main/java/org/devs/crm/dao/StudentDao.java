package org.devs.crm.dao;

import org.devs.crm.entity.Student;

import java.util.List;

public interface StudentDao extends BaseDao<Student> {

    List<Student> findAllByGroupId(Long groupId);

}
