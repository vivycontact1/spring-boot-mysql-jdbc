package com.vivy.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vivy.models.FileModel;
import com.vivy.utils.Constants;

@Repository
public class FileDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int add(FileModel model) {
		String strQuery = "";
		strQuery = "insert into "+Constants.TABLE_NAME_FILE+" (name,type,fileSize,fileName) Values ('"
				+ model.getName() + "','" + model.getType() + "','"
				+ model.getFileSize() + "','" + "','"
				+ model.getFileName() + "')";
		
		return jdbcTemplate.update(strQuery);
		
	}
	
	
	public int update(FileModel model) {
		String strQuery = "";
		int i = -1;
		try{
			strQuery = "update "+Constants.TABLE_NAME_FILE+" set name = '"+model.getName()+"',type = '"+model.getType()
			+"',fileSize = '"+model.getFileSize()+"',fileName = '"+model.getFileName()
			+"'where id = "+model.getId();
			i = jdbcTemplate.update(strQuery);
		}catch(DataAccessException de){
			de.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return i;
	}
	
	public int delete(int id) {
		String strQuery = "";
		int i = -1;
		
		try{
			strQuery = "delete  from "+Constants.TABLE_NAME_FILE+" where id = "+id;
			i = jdbcTemplate.update(strQuery);
		}catch(DataAccessException de){
			de.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return i;
	}
	
	
	public FileModel findFileById(int id){
		FileModel model = new FileModel();
		try{
			String strQuery = "select * from "+Constants.TABLE_NAME_FILE+" where id="+id;
			model =  (FileModel)jdbcTemplate.queryForObject(strQuery,new FileRowMapper());
		}catch(DataAccessException de){
			de.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return model;
	}
	
	
	public List<FileModel> findAll(){
		List<FileModel> list = null;
		try{
			String strQuery = "select * from "+Constants.TABLE_NAME_FILE;
			list = jdbcTemplate.query(strQuery,new FileRowMapper());
		}catch(DataAccessException de){
			de.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	

	class FileRowMapper implements RowMapper<FileModel>
	{
	    @Override
	    public FileModel mapRow(ResultSet rs, int rowNum) throws SQLException 
	    {
	    	FileModel mdl = new FileModel();
	    	
	    	mdl.setId(rs.getInt("id"));
	    	mdl.setName(rs.getString("name"));
	    	mdl.setType(rs.getString("type"));
	    	mdl.setFileSize(rs.getInt("fileSize"));
	    	mdl.setFileName(rs.getString("fileName"));
	    	mdl.setCreatedDate(rs.getString("createdDate"));
			
	        return mdl;
	    }
	}


}