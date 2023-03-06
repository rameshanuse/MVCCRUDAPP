package in.ineuron.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.ineuron.dto.Student;
import in.ineuron.util.JdbcUtil;

public class StudentDaoImpl implements IStudentDao {
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	
	@Override
	public String addStudent(Student student) {
		
		String sqlInsertQuery = "insert into student(`name`,`age`,`address`)values(?,?,?)";
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			if(connection != null) 
				pstmt = connection.prepareStatement(sqlInsertQuery);
			if(pstmt != null) {
				
				pstmt.setString(1, student.getSname());
				pstmt.setInt(2, student.getSage());
				pstmt.setString(3, student.getSaddress());
				
				int rowAffected = pstmt.executeUpdate();
				if(rowAffected == 1) {
					return "success";
				} 
				
			}
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return "failure";
	}
	
	@Override
	public Student searchStudent(Integer sid) {
		String sqlSelectQuery ="select id, name, age, address from student where id = ?";
		Student student = null;
		
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			if(connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
			
			if(pstmt != null) {
				pstmt.setInt(1, sid);
			}
			
			if(pstmt != null) {
				resultSet = pstmt.executeQuery();
			}
			
			if(resultSet != null) {
				
				if(resultSet.next()){
					student = new Student();
					
					//Copying resultSet data to student object
					student.setSid(resultSet.getInt(1));
					student.setSname(resultSet.getString(2));
					student.setSage(resultSet.getInt(3));
					student.setSaddress(resultSet.getString(4));
					
					return student;
				}
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	@Override
	public String updateStudent(Student student) {
		String sqlUpdaeQuery = "update student set name = ?, age = ?, address = ? where id = ? ";
		
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			if(connection != null) 
				pstmt = connection.prepareStatement(sqlUpdaeQuery);
			
			if(pstmt != null) {
				
				pstmt.setString(1, student.getSname());
				pstmt.setInt(2, student.getSage());
				pstmt.setString(3, student.getSaddress());
				pstmt.setInt(4, student.getSid());
				
				int rowAffected = pstmt.executeUpdate();
				
				if(rowAffected == 0) {
					return "not found";
				} else if(rowAffected == 1) {
					return "success";
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

		return "failure";
	}
	
	@Override
	public String deleteStudent(Integer sid) {
		String sqlDeleteQuery ="delete from student where id = ?";
		
		try {
			connection = JdbcUtil.getJdbcConnection();
			
			if(connection != null) 
				pstmt = connection.prepareStatement(sqlDeleteQuery);
			
			if(pstmt != null) {
				pstmt.setInt(1, sid);
			}
			
			if(pstmt != null) {
				int rowAffected = pstmt.executeUpdate();
				
				if(rowAffected == 0) {
					return "not found";
				} else {
					return "success";
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		return "failure";
	}
}
