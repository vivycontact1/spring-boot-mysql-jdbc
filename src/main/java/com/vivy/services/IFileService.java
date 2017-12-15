package com.vivy.services;

import java.util.List;

import com.vivy.models.FileModel;

public interface IFileService {
	
	
	public int add(FileModel s);
	public int update(FileModel s);
	public int save(FileModel s);
	public int delete(int id);
	public List<FileModel> findAll();
	public FileModel findFileById(int id);
	

}
