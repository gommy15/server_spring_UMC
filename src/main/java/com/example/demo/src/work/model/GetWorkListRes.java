package com.example.demo.src.work.model;

import com.example.demo.src.work.model.GetWorkRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetWorkListRes {
    private List<GetWorkRes> getWorkRes;
}
