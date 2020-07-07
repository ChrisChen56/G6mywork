package com.mbrpf.model;

import java.sql.Connection;
import java.util.*;

public interface MbrpfDAO_interface {
          public void insert(MbrpfVO mbrpfVO);
          public void update(MbrpfVO mbrpfVO);
          public void delete(String mbrno);
          public MbrpfVO findByPrimaryKey(String mbrno);
          public List<MbrpfVO> getAll();
        //萬用複合查詢(傳入參數型態Map)(回傳 List)
          public MbrpfVO findByMbract(String mbract);
          public MbrpfVO login(String mbract);
//        public List<MbrpfVO> getAll(Map<String, String[]> map);
          public void updatePoint(MbrpfVO mbrpfVO ,Connection conn);
          public void updatePoint(MbrpfVO mbrpfVO);
}
