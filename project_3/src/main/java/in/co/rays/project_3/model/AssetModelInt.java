package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AssetModelInt {

	public long add(AssetDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(AssetDTO dto)throws ApplicationException;
	public void update(AssetDTO dto)throws ApplicationException,DuplicateRecordException;
	public AssetDTO findByPK(long pk)throws ApplicationException;
	public AssetDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(AssetDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(AssetDTO dto)throws ApplicationException;
	public List getRoles(AssetDTO dto)throws ApplicationException;
	



}
