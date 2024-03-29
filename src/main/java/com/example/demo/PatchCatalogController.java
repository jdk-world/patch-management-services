package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/patch")
public class PatchCatalogController {

    // Inject a service or repository that handles data access
    private final PatchCatalogService patchCatalogService;

    @Autowired
    public PatchCatalogController(PatchCatalogService patchCatalogService) {
        this.patchCatalogService = patchCatalogService;
    }

    @GetMapping("/catelog")
    public List<Patch> getPatches() {
        return patchCatalogService.getAllPatches();
    }
    
	@RequestMapping(value = "/create-patch", method = RequestMethod.POST)
	@ResponseBody
	public String createPatch(@RequestBody Patch patchModel)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		String msg = "";
		msg = patchCatalogService.createPatch(patchModel.getName(), patchModel.getDescription(),
					patchModel.getVersion(), patchModel.getReleaseDate(), patchModel.getComplianceDate(),
					patchModel.getApplication(), patchModel.getCompatibility(),patchModel.getCompatibility(),patchModel.getIsActive());
		//model.addAttribute("msg", "Sucessfully created");

		return msg;

	}


	@RequestMapping(value = "/remove-patch", method = RequestMethod.POST)
	@ResponseBody
	public String deletePatchs(@RequestBody List<String> slotIdList)
				throws ParseException, IOException, GeneralSecurityException {
		
		String report = patchCatalogService.removePatches(slotIdList);
		return report;

	}
	
    
	@RequestMapping(value = "/tag", method = RequestMethod.POST)
	@ResponseBody
	public String tagPatch(@RequestBody TagRequest tagRequestModel)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		String msg = "";
		msg = patchCatalogService.tagPatchsToEmps(tagRequestModel);
		//model.addAttribute("msg", "Sucessfully created");

		return msg;

	}

	@RequestMapping(value = "/untag", method = RequestMethod.POST)
	@ResponseBody
	public String untagPatch(@RequestBody TagRequest tagRequestModel)
			throws ParseException, IOException, GeneralSecurityException {
		// System.err.println(BASE_PATH_CLOUD);
		// utilService.setBASE_PATH_CLOUD(BASE_PATH_CLOUD);
		String msg = "";
		msg = patchCatalogService.untagPatchsToEmps(tagRequestModel);
		//model.addAttribute("msg", "Sucessfully created");

		return msg;

	}


}
