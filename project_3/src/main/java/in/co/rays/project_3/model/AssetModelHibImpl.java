package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AssetModelHibImpl implements AssetModelInt {

	public long add(AssetDTO dto) throws ApplicationException, DuplicateRecordException {


		AssetDTO existDto = null;
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			session.save(dto);

			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Employee Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();

	}

	
	public void delete(AssetDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Employee Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}


	public void update(AssetDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Employee update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	

	public AssetDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		AssetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (AssetDTO) session.get(AssetDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Employee by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	

	public AssetDTO findByLogin(String login) throws ApplicationException {
		Session session = null;
		AssetDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AssetDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (AssetDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Employee by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AssetDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Employees list");
		} finally {
			session.close();
		}

		return list;
	}

	
	public List search(AssetDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	
	public List search(AssetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<AssetDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AssetDTO.class);
			if (dto != null) {
				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getAssetId() != null && dto.getAssetId() > 0) {
					criteria.add(Restrictions.eq("assetId", dto.getAssetId()));
				}

								
				  if (dto.getRegistrationNumber() != null && dto.getRegistrationNumber().length() > 0) {
				  criteria.add(Restrictions.like("registrationNumber", dto.getRegistrationNumber() + "%"));
				  }

				  if (dto.getPaintColor() != null && dto.getPaintColor().length() > 0) {
				  criteria.add(Restrictions.like("paintColor", dto.getPaintColor() + "%"));
				  }
				  
				
				
				  if (dto.getCoverageAmount() != null && dto.getCoverageAmount() > 0) {
				  criteria.add(Restrictions.eq("coverageAmount", dto.getCoverageAmount())); }
				 
				
				if (dto.getAcquisitionDate() != null && dto.getAcquisitionDate().getTime() > 0) {
					criteria.add(Restrictions.eq("acquisitionDate", dto.getAcquisitionDate()));
				}
				
				
			}
			
			
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<AssetDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Employee search");
		} finally {
			session.close();
		}

		return list;
	}

	

	public List getRoles(AssetDTO dto) throws ApplicationException {
		return null;
	}



	
	

	



}
