package org.devs.data.dao.impl;

import java.util.List;
import java.util.Optional;
import org.devs.data.dao.GroupDao;
import org.devs.data.dao.StudentDao;
import org.devs.data.dao.impl.query.StudentQuery;
import org.devs.data.dao.impl.rowmapper.StudentRowMapper;
import org.devs.data.entity.Student;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl implements StudentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final StudentRowMapper studentRowMapper;
    private final GroupDao groupDao;

    public StudentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, StudentRowMapper studentRowMapper, @Lazy GroupDao groupDao) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.studentRowMapper = studentRowMapper;
        this.groupDao = groupDao;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return namedParameterJdbcTemplate.query(StudentQuery.SELECT_ONE,
                new MapSqlParameterSource("id", id), studentRowMapper).stream().findFirst();
    }


    @Override
    public Student save(Student student) {
        KeyHolder holder = new GeneratedKeyHolder();

        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("fname", student.getFirstName())
                .addValue("lname", student.getLastName())
                .addValue("patronymic", student.getPatronymic())
                .addValue("email", student.getEmail())
                .addValue("phoneNumber", student.getPhoneNumber());

        namedParameterJdbcTemplate.update(StudentQuery.SAVE_STUDENT, source, holder, new String[]{"id"});
        student.setId(holder.getKeyAs(Long.class));

        return student;
    }

    @Override
    public List<Student> findAllByGroupId(Long groupId) {
        return namedParameterJdbcTemplate.query(StudentQuery.SELECT_ALL_BY_GROUP,
                new MapSqlParameterSource().addValue("id", groupId), studentRowMapper);
    }
}
