package kr.ac.hufs.ice.ice.repository;

import kr.ac.hufs.ice.ice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

