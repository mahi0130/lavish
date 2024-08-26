package in.co.rays.project_3.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.AssetDTO;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AssetModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AssetListCtl", urlPatterns = { "/ctl/AssetListCtl" })

public class AssetListCtl extends BaseCtl{

	
	@Override
	protected void preload(HttpServletRequest request) {
		

		Map<Integer, String> map1 = new HashMap();
		map1.put(1, "Red");
		map1.put(2, "Blue");
		map1.put(3, "Black");
		map1.put(4, "Yellow");
		
		request.setAttribute("map", map1);

		

		
		 	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		AssetDTO dto = new AssetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAssetId(DataUtility.getLong(request.getParameter("assetId")));

		

		dto.setRegistrationNumber(DataUtility.getString(request.getParameter("registrationNumber")));
        dto.setAcquisitionDate(DataUtility.getDate(request.getParameter("acquisitionDate")));

		dto.setCoverageAmount(DataUtility.getLong(request.getParameter("coverageAmount")));
		dto.setPaintColor(DataUtility.getString(request.getParameter("paintColor")));



		populateBean(dto, request);
		return dto;
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		AssetDTO dto = (AssetDTO) populateDTO(request);

		AssetModelInt model = ModelFactory.getInstance().getAssetModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList a = (ArrayList<AssetDTO>) list;

			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		AssetDTO dto = (AssetDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		AssetModelInt model = ModelFactory.getInstance().getAssetModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				
				
			/*	if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
					String assetId  = request.getParameter("assetId");
					String registrationNumber = request.getParameter("registrationNumber");
					String coverageAmount = request.getParameter("coverageAmount");
					String acquisitionDate = request.getParameter("acquisitionDate");
					String PaintColor = request.getParameter("PaintColor");

				
					if (assetId.isEmpty() && PaintColor.equals("") && registrationNumber.equals("")  &&  acquisitionDate.isEmpty() && coverageAmount.isEmpty()) {

						ServletUtility.setErrorMessage("Pleace fill at least on search feald", request);
					}

				} */
				
				if (OP_SEARCH.equalsIgnoreCase(op)) {
				    pageNo = 1;
				    String assetId = request.getParameter("assetId");
				    String registrationNumber = request.getParameter("registrationNumber");
				    String coverageAmount = request.getParameter("coverageAmount");
				    String acquisitionDate = request.getParameter("acquisitionDate");
				    String paintColor = request.getParameter("paintColor");

				    if ((assetId == null || assetId.isEmpty()) &&
				        (paintColor == null || paintColor.isEmpty()) &&
				        (registrationNumber == null || registrationNumber.isEmpty()) &&
				        (acquisitionDate == null || acquisitionDate.isEmpty()) &&
				        (coverageAmount == null || coverageAmount.isEmpty())) {
				        
				        ServletUtility.setErrorMessage("Please fill at least one search field", request);
				    }
				}

				else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ASSET_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ASSET_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					AssetDTO deletedto = new AssetDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ASSET_LIST_CTL, request, response);
				return;
			}
			dto = (AssetDTO) populateDTO(request);
			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ASSET_LIST_VIEW;
	}





}
