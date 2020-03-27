package io.domisum.janus.config.object.project;

import io.domisum.janus.config.object.ConfigObject;
import io.domisum.janus.config.object.ValidationReport;
import io.domisum.lib.auxiliumlib.PHR;
import io.domisum.lib.auxiliumlib.exceptions.InvalidConfigurationException;
import io.domisum.lib.auxiliumlib.util.StringUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Project
		implements ConfigObject
{
	
	// ATTRIBUTES
	@Getter
	private final String id;
	
	private final String buildRootDirectory;
	@Getter
	private final String exportDirectory;
	
	// COMPONENTS
	private final List<ProjectComponent> components;
	
	// DEPENDENCY
	private final ProjectDependencyFacade projectDependencyFacade;
	
	
	// INIT
	@Override
	public ValidationReport validate()
			throws InvalidConfigurationException
	{
		var validationReport = new ValidationReport();
		
		Validate.notNull(id, "id has to be set");
		Validate.isTrue(!(buildRootDirectory == null && exportDirectory == null), "either buildRootDirectory or exportDirectory has to be set");
		if(buildRootDirectory != null)
			Validate.isTrue(id.equals(getBuildRootDirectory().getName()), "the name of buildRootDirectory has to be the id of the project");
		validationReport.noteFieldValue(buildRootDirectory, "buildRootDirectory");
		validationReport.noteFieldValue(exportDirectory, "exportDirectory");
		validateComponents(validationReport);
		
		return validationReport.complete();
	}
	
	private void validateComponents(ValidationReport validationReport)
			throws InvalidConfigurationException
	{
		for(int i = 0; i < components.size(); i++)
		{
			var projectComponent = components.get(i);
			try
			{
				var componentValidationReport = projectComponent.validate();
				validationReport.addSubreport(componentValidationReport, projectComponent.getComponentId());
			}
			catch(InvalidConfigurationException|IllegalArgumentException e)
			{
				throw new InvalidConfigurationException("configuration error in projectComponent at index "+i, e);
			}
			
			projectDependencyFacade.validateComponentExists(projectComponent.getComponentId());
		}
	}
	
	
	// OBJECT
	@Override
	public String toString()
	{
		var componentIds = new ArrayList<>();
		for(var component : components)
			componentIds.add(component.getComponentId());
		String componentDisplay = StringUtil.listToString(componentIds, ", ");
		
		return PHR.r("Project({}: components=({}))", id, componentDisplay);
	}
	
	
	// GETTERS
	public File getBuildRootDirectory()
	{
		if(buildRootDirectory == null)
			return null;
		return new File(buildRootDirectory);
	}
	
	public List<ProjectComponent> getComponents()
	{
		return new ArrayList<>(components);
	}
	
	
	// COMPONENT
	@RequiredArgsConstructor
	public static class ProjectComponent
	{
		
		@Getter
		private final String componentId;
		private final String directoryInBuild;
		
		
		// INIT
		public ValidationReport validate()
		{
			var validationReport = new ValidationReport();
			
			Validate.notNull(componentId, "componentId can't be null");
			validationReport.noteFieldValue(directoryInBuild, "directoryInBuild");
			
			return validationReport.complete();
		}
		
	}
	
}