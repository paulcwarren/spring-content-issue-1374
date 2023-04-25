package gettingstarted;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@PreAuthorize("@debugService.debug('FileRepository class level security') AND true")
public interface FileRepository extends JpaRepository<File, Long> {

    @Override
    @PreAuthorize("@debugService.debug('FileRepository findById security') AND true")
    public Optional<File> findById(Long id);

    @Override
    @PreAuthorize("@debugService.debug('FileRepository save security') AND true")
    public File save(File entity);

}
