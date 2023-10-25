package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PatchCatalogService {
	@Autowired
	JdbcTemplate jdbc;

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
			obj.setApplicabilityStatus(((String) row.get("applicabilityStatus")).toString());

			obj.setCompatibility(((String) row.get("compatibility")));
			obj.setIsActive(((String) row.get("active")));

			patchs.add(obj);
		}

		return patchs;

	}

	public String createPatch(String name, String description, String version, String releaseDate,
			String complianceDate, String application, String string, String compatibility, String isActive) {
		String query = "INSERT INTO `springbootdb`.`PatchCatalog` (`name`, `description`, `version`, `releaseDate`, `complianceDate`, `application`, `applicabilityStatus`, `compatibility`, `active`) VALUES ('"
				+ name + "', '" + description + "', '" + version + "', '" + releaseDate + "', '" + complianceDate
				+ "', '" + application + "', 'Applicable', '" + compatibility + "', '" + isActive + "');\n" + "";

		jdbc.execute(query);
		return "Catelog entry created Successfully";
	}
	
	public String removePatch(Patch patch) {

		String query= "DELETE from `springbootdb`.`Admin` WHERE roll_no = "+patch.getId() +";";

		jdbc.execute(query);
		return "Catelog entry deleted Successfully";
	}
		
}
