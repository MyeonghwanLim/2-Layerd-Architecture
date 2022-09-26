package com.lim.biz.board.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.lim.biz.board.BoardVO;
import com.lim.biz.common.JDBCUtil;


public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	
	final String sql_selectOne="SELECT * FROM BOARD WHERE BID=?";
	final String sql_selectAll="SELECT * FROM BOARD ORDER BY BID DESC";
	final String sql_insert="INSERT INTO BOARD(BID,TITLE,WRITER,CONTENT) VALUES((SELECT NVL(MAX(BID),0)+1 FROM BOARD),?,?,?)";
	final String sql_update="UPDATE BOARD SET TITLE=?,CONTENT=? WHERE BID=?";
	final String sql_delete="DELETE BOARD WHERE BID=?";
	final String sql_selectAll_T="SELECT * FROM BOARD  WHERE TITLE LIKE '%'||?||'%'  ORDER BY BID ASC";
	final String sql_selectAll_W="SELECT * FROM BOARD  WHERE WRITER LIKE '%'||?||'%' ORDER BY BID ASC";
	
	public boolean insertBoard(BoardVO vo) {
		
		conn=JDBCUtil.connect(); 
		try {
			pstmt=conn.prepareStatement(sql_insert);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getContent());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return false;
	}
	public void updateBoard(BoardVO vo) {
		
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3,vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
	}
	public void deleteBoard(BoardVO vo) {
	
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setInt(1,vo.getBid());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
	}
	public BoardVO selectOneBoard(BoardVO vo) {
		
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setInt(1, vo.getBid());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				BoardVO data=new BoardVO();
				data.setBid(rs.getInt("BID"));
				data.setContent(rs.getString("CONTENT"));
				data.setTitle(rs.getString("TITLE"));
				data.setWriter(rs.getString("WRITER"));
				data.setCnt(rs.getInt("CNT"));
				data.setRegdate(rs.getString("REGDATE"));
				return data;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;
	}
	public List<BoardVO> selectAllBoard(BoardVO vo){
		System.out.println("BoardDAO selectAll의 로그: "+vo);
		List<BoardVO> datas=new ArrayList<BoardVO>();
		conn=JDBCUtil.connect();
		try {
			if(vo.getSearchCondition()==null) {
				vo.setSearchCondition("TITLE");
			}
			if(vo.getSearchContent()==null) {
				vo.setSearchContent("");
			}
			String sql_selectAll=sql_selectAll_T;
			if(vo.getSearchCondition().equals("WRITER")) {
			sql_selectAll=sql_selectAll_W;
				
			}
			
			pstmt=conn.prepareStatement(sql_selectAll);
			pstmt.setString(1, vo.getSearchContent());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				BoardVO data=new BoardVO();
				data.setBid(rs.getInt("BID"));
				data.setTitle(rs.getString("TITLE"));
				data.setWriter(rs.getString("WRITER"));
				data.setContent(rs.getString("CONTENT"));
				data.setCnt(rs.getInt("CNT"));
				data.setRegdate(rs.getString("REGDATE"));
				datas.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}		
		return datas;
	}
}
