package com.example.demo.src.work;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.work.model.GetWorkListRes;
import com.example.demo.src.work.model.PostWorkReq;
import com.example.demo.src.work.model.PostWorkRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/todo")
public class WorkController {
    private final WorkProvider workProvider;
    private final WorkService workService;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WorkController(WorkProvider workProvider, WorkService workService, JwtService jwtService) {
        this.workProvider = workProvider;
        this.workService = workService;
        this.jwtService = jwtService;
    }

    //투두 조회하기
    @ResponseBody
    @GetMapping("/{todoDate}")
    public BaseResponse<GetWorkListRes> getWorkList(@PathVariable("todoDate") String todoDate) {

        try{
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy-MM-dd");
            // String 타입을 Date 타입으로 변환
            Date formatDate = dtFormat.parse(todoDate);

            int userIdxByJwt = jwtService.getUserIdx();
            System.out.println();

            GetWorkListRes getWorkRes = workProvider.retrieveWork(userIdxByJwt, formatDate);
            return new BaseResponse<>(getWorkRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //투두 생성하기
    @ResponseBody
    @PostMapping("/{todoDate}")
    public BaseResponse<PostWorkRes> getWorkByIdx(@PathVariable("todoDate") String todoDate, @RequestParam("todoName") String postWorkReq) {

        try{
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            // String 타입을 Date 타입으로 변환
            Date formatDate = dtFormat.parse(todoDate);

            int userIdxByJwt = jwtService.getUserIdx();

            PostWorkRes postWorkRes = workService.createWork(userIdxByJwt, formatDate, postWorkReq);
            return new BaseResponse<>(postWorkRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //todo리스트 수정하기
    @ResponseBody
    @PatchMapping("/{todoIdx}")
    public BaseResponse<String> modifyTodo(@PathVariable("todoIdx") int workIdx, @RequestParam("todoName") PostWorkReq postWorkReq) {
        //public BaseResponse<String> modifyTodo(@PathVariable ("todoIdx") int todoIdx, @RequestBody PostTodoReq postTodoReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            workService.modifyWork(userIdxByJwt, workIdx, postWorkReq);
            String result = " To Do List 수정을 완료하였습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //todo리스트 삭제
    @ResponseBody
    @PatchMapping("/{todoIdx}/status")
    public BaseResponse<String> deleteTodo(@PathVariable ("todoIdx") int workIdx) {
        try{
            workService.deleteWork(workIdx);
            String result = "To Do List 삭제를 성공했습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}