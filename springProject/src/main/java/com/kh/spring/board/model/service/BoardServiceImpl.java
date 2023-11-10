package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private SqlSessionTemplate sqlsession;
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public int selectListCount() {
		return boardDao.selectListCount(sqlsession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return boardDao.selectList(sqlsession, pi);
	}

	@Override
	public int insertBoard(Board b) {
		return boardDao.insertBoard(sqlsession, b);
	}

	@Override
	public Board selectBoard(int boardNo) {
		return boardDao.selectBoard(sqlsession, boardNo);
	}

	@Override
	public int increaseCount(int boardNo) {
		return boardDao.increaseCount(sqlsession, boardNo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return boardDao.deleteBoard(sqlsession, boardNo);
	}

	@Override
	public int updateBoard(Board b) {
		return boardDao.updateBoard(sqlsession, b);
	}

}
