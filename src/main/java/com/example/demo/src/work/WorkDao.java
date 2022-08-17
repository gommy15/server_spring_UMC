package com.example.demo.src.work;

import com.example.demo.src.work.model.GetWorkRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Repository
public class WorkDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //조회
    public List<GetWorkRes> selectWork(int userIdx, Date todoDate){
        String selectWorkQuery = "SELECT todoName \n" +
                "FROM todoList \n" +
                "WHERE userIdx = ? and todoDate = ? and complete = 0";
        Object[] selectWorkParam = new Object[]{userIdx, todoDate};
        return this.jdbcTemplate.query(selectWorkQuery,
                (rs, rowNum) -> new GetWorkRes(
                        rs.getString("todoName")
                ), selectWorkParam);
    }

    //생성
    public int insertWork(int userIdx, Date todoDate, String workName){
        String insertWorkQuery = "INSERT into todoList(userIdx, todoDate, complete, todoName) VALUES(?, ?, 0, ?)";
        Object[] insertWorkParams = new Object[]{userIdx, todoDate, workName};
        this.jdbcTemplate.update(insertWorkQuery, insertWorkParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //수정
    public int updateWork(int userIdx, int workIdx, String workName){
        String updateQuery = "UPDATE todoList SET todoName = ? WHERE userIdx = ? and todoIdx = ?" ;
        Object[] updateParams = new Object[]{workName, userIdx, workIdx};

        return this.jdbcTemplate.update(updateQuery,updateParams);
    }

    //삭제
    public int deleteWork (int workIdx){
        String deleteQuery = "UPDATE todoList SET status = 'INACTIVE' WHERE todoIdx = ?";
        Object[] deleteParams = new Object[] {workIdx};

        return this.jdbcTemplate.update(deleteQuery,deleteParams);
    }
}
