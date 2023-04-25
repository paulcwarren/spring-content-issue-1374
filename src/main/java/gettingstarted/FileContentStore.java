package gettingstarted;

import org.springframework.content.commons.property.PropertyPath;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.commons.repository.GetResourceParams;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component  // just to keep the ide happy!
@PreAuthorize("@debugService.debug('FileContentStore class level security') AND true")
public interface FileContentStore extends ContentStore<File, String> {

	@PreAuthorize("@debugService.debug('FileContentStore getResource security') AND true")
	@Override
	Resource getResource(File entity, PropertyPath propertyPath, GetResourceParams params);

	@PreAuthorize("@debugService.debug('FileContentStore setContent security') AND true")
	@Override
	File setContent(File entity, PropertyPath propertyPath, InputStream content, long contentLen);

}
