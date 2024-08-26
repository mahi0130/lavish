
package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.TransactionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface TransactionModelInt {
	
	public long add(TransactionDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(TransactionDTO dto)throws ApplicationException;
	public void update(TransactionDTO dto)throws ApplicationException,DuplicateRecordException;
	public TransactionDTO findByPK(long pk)throws ApplicationException;
	public TransactionDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(TransactionDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(TransactionDTO dto)throws ApplicationException;
	public List getRoles(TransactionDTO dto)throws ApplicationException;
	

}
