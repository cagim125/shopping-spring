package io.getarrays.contactapi.commnet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommnetRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductId(Long pid);

    Optional<Comment> findByUserIdAndProductId(Long pid, Long uid);
}
