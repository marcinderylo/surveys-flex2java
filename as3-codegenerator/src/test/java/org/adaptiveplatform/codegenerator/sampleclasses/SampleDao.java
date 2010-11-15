package org.adaptiveplatform.codegenerator.sampleclasses;

import java.util.List;

import org.adaptiveplatform.codegenerator.api.RemoteService;

@RemoteService
public interface SampleDao {
	SampleDto getUser(Long id);

	List<SampleDto> query(String text);

	int count(List<String> roles);
}
