package com.vivy.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivy.dao.impl.FileDao;
import com.vivy.models.FileModel;
import com.vivy.services.IFileService;


@Service
public class FileService implements IFileService {

	@Autowired
	FileDao fileDao;
	
	@Override
	public int add(FileModel s) {
		int result = -1;
		
		try{
			return fileDao.add(s);
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int update(FileModel s) {
		int result = -1;
		
		try{
			result = fileDao.update(s);
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int save(FileModel s) {
		int result = -1;
		try{
			if(s.getId()==0){
				result = fileDao.add(s);
			}else{
				result = fileDao.update(s);
			}
		}catch (Exception e) {
			
		}
		return result;
	}

	@Override
	public int delete(int id) {
		int result = -1;
		
		try{
			result = fileDao.delete(id);
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public List<FileModel> findAll() {
		List<FileModel>  result = null;
		
		try{
			result = fileDao.findAll();
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public FileModel findFileById(int id) {
		FileModel result = null;
		
		try{
			result = fileDao.findFileById(id);
		}catch(Exception e){
			
		}
		// TODO Auto-generated method stub
		return result;
	}

}
