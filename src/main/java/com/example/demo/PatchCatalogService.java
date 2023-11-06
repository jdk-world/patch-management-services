package com.example.demo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class PatchCatalogService {
	@Autowired
	JdbcTemplate jdbc;
	public static final String DEFAULT_DATE_UNCOMPLETION = "1900-01-01";//2023-11-02T11:01//2023-10-01
	public static final String DEFAULT_PATCH_UNCOMPLIANCE = "Non-compliant";

	public static final String DEFAULT_PATCH_COMPLIANCE = "Compliant";
    public static final LocalDate DEFAULT_DATE_COMPLETION = LocalDate.now();

	public List<Patch> getAllPatches() {

		String sql = "SELECT * FROM PatchCatalog";

		List<Patch> patchs = new ArrayList<>();

		List<Map<String, Object>> rows = jdbc.queryForList(sql);

		for (Map row : rows) {
			Patch obj = new Patch();
			obj.setId(((Integer) row.get("id")));

			obj.setName(((String) row.get("name")).toString());
			obj.setDescription(((String) row.get("description")).toString());
			obj.setVersion(((String) row.get("version")).toString());
			obj.setReleaseDate(row.get("releaseDate").toString());
			obj.setComplianceDate(row.get("complianceDate").toString());

			obj.setApplication(((String) row.get("application")).toString());
			obj.setApplicabilityStatus(((String) row.get("applicability")).toString());

			obj.setCompatibility(((String) row.get("compatibility")));
			obj.setIsActive(((String) row.get("active")));

			patchs.add(obj);
		}

		return patchs;

	}

	public String createPatch(String name, String description, String version, String releaseDate,
			String complianceDate, String application, String applicability, String compatibility, String isActive) {
		String query = "INSERT INTO `springbootdb`.`PatchCatalog` (`name`, `description`, `version`, `releaseDate`, `complianceDate`, `application`, `applicability`, `compatibility`, `active`) VALUES ('"
				+ name + "', '" + description + "', '" + version + "', '" + releaseDate + "', '" + complianceDate
				+ "', '" + application + "', '" + applicability + "', '" + compatibility + "', '" + isActive + "');\n" + "";

		jdbc.execute(query);
		return "Catelog entry created Successfully";
	}
		
	
	public String removePatches(List<String> patchIdList) {
	    if (patchIdList.isEmpty()) {
	        return "No slots to delete";
	    }

	    // Create a comma-separated list of slot IDs
	    String slotIds = String.join(",", patchIdList);

	    String query = "DELETE FROM `springbootdb`.`PatchCatalog` WHERE id IN (" + slotIds + ");";

	    jdbc.execute(query);

	    return "Patch entries " + patchIdList + " deleted successfully";
	}


	public String tagPatchsToEmps(TagRequest tagRequestModel) {
		String msg = "";

		if (tagRequestModel.getEmpIdsList() != null && !tagRequestModel.getEmpIdsList().isEmpty()) {
			// Convert the list of applicable patch IDs to a comma-separated string
			String applicablePatchIds = String.join(", ", tagRequestModel.getPatchIdsList());

			// Use JDBC to insert new rows for each unique applicable_patch_id
			String insertSql = "INSERT INTO Employee (roll_no, emp_name, emp_region, applicable_patch_id, patch_compliance, date_of_completion, e_mail_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

			for (String empId : tagRequestModel.getEmpIdsList()) {
				// No need to split patchIdsArray, it's a single patch ID

				// Fetch existing employee details
				String sql = "SELECT * FROM Employee WHERE roll_no = ?";
				String emp_name = "", emp_region = "", e_mail_id = "",patch_compliance="" ; Integer applicable_patch_id;
				List<Map<String, Object>> rows = jdbc.queryForList(sql, empId);
				HashMap<Integer, String> setOfAlreadyAppliedPatchs = new HashMap<Integer, String>();

				for (Map row : rows) {
					emp_name = (String) row.get("emp_name");
					emp_region = (String) row.get("emp_region");
					e_mail_id = (String) row.get("e_mail_id");
					patch_compliance = (String) row.get("patch_compliance");
					applicable_patch_id = (Integer) row.get("applicable_patch_id");
					
					setOfAlreadyAppliedPatchs.put(applicable_patch_id,patch_compliance );

				} // Insert a new row for the unique applicable_patch_id

				for (String patchId : tagRequestModel.getPatchIdsList()) {

					if (!setOfAlreadyAppliedPatchs.containsKey(Integer.parseInt(patchId)))
						jdbc.update(insertSql, empId, emp_name, emp_region, patchId, DEFAULT_PATCH_UNCOMPLIANCE,
								DEFAULT_DATE_UNCOMPLETION, e_mail_id);
					
					else if (setOfAlreadyAppliedPatchs.containsKey(Integer.parseInt(patchId)) && setOfAlreadyAppliedPatchs.containsValue(DEFAULT_PATCH_COMPLIANCE))
						jdbc.update(insertSql, empId, emp_name, emp_region, patchId, DEFAULT_PATCH_UNCOMPLIANCE,
								DEFAULT_DATE_UNCOMPLETION, e_mail_id);
				}
			}

			msg = "Patch IDs " + tagRequestModel.getPatchIdsList() + " added successfully for selected employees "
					+ tagRequestModel.getEmpIdsList() + ".";
		}

		return msg;
	}
	
	
	public String untagPatchsToEmps(TagRequest tagRequestModel) {
		String msg = "";

		if (tagRequestModel != null && tagRequestModel.getEmpIdsList() != null
				&& !tagRequestModel.getEmpIdsList().isEmpty() && tagRequestModel.getPatchIdsList() != null
				&& !tagRequestModel.getPatchIdsList().isEmpty()) {
			String updateSql = StringUtils.EMPTY;

			for (String empId : tagRequestModel.getEmpIdsList()) {
				for (String patchId : tagRequestModel.getPatchIdsList()) {
					updateSql = "UPDATE Employee " + "SET patch_compliance = '" + DEFAULT_PATCH_COMPLIANCE + "', "
							+ "date_of_completion = '" + DEFAULT_DATE_COMPLETION + "' " + "WHERE roll_no = '" + empId + "' "
							+ "AND applicable_patch_id = '" + patchId + "'";

							jdbc.update(updateSql);

					msg = "Patch ID " + patchId + " untagged successfully for employee " + empId + ".";

				}
			}
		} else {
			msg = "No employees selected for update.";
		}

		return msg;
	}
	
}
