package org.adaptiveplatform.codegenerator.acceptance;

import java.util.List;

import org.adaptiveplatform.codegenerator.api.RemoteService;

@RemoteService
interface SampleDao {
	SampleDto getUser(Long id);

	List<SampleDto> query(String text);

	int count(List<String> roles);
}