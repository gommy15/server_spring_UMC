package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.src.work.model.PostWorkReq;
import com.example.demo.src.work.model.PostWorkRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class WorkService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WorkDao workDao;
    private final WorkProvider workProvider;
    private final JwtService jwtService;


    @Autowired
    public WorkService(WorkDao workDao, WorkProvider workProvider, JwtService jwtService) {
        this.workDao = workDao;
        this.workProvider = workProvider;
        this.jwtService = jwtService;

    }

    // todo리스트 생성
    public PostWorkRes createWork(int userIdx, Date todoDate, String postWorkReq) throws BaseException {

        try{
            int workIdx = workDao.insertWork(userIdx, todoDate, postWorkReq);
            return new PostWorkRes(workIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // todo리스트 수정
    public void modifyWork(int userIdx, int workIdx, PostWorkReq postWorkReq) throws BaseException {

        try{
            //todoDao가 잘 실행되면 1, 아니면 0을 전달 받아 error 코드 표시
            int result = workDao.updateWork(userIdx, workIdx, postWorkReq.getWorkName());

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_POST);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //todo리스트 삭제
    public void deleteWork(int workIdx) throws BaseException{
        try{
            //todoDao가 잘 실행되면 1, 아니면 0을 전달 받아 error 코드 표시
            int result = workDao.deleteWork(workIdx);

            if(result == 0){
                throw new BaseException(DELETE_FAIL_POST);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
