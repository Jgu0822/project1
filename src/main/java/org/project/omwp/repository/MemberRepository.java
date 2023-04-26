package org.project.omwp.repository;

import org.project.omwp.dto.MemberDto;
import org.project.omwp.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUserEmail(String userEmail);

    Page<MemberEntity> findByUserNameContaining(String keyword, Pageable pageable);

    Page<MemberEntity> findByUserEmailContaining(String keyword, Pageable pageable);

    Page<MemberEntity> findByUserRole(String keyword, Pageable pageable);

    Page<MemberEntity> findByUserId(Long userId, Pageable pageable);

    /* 이메일, 닉네임 중복 관련 */
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserName(String userName);

    // 이메일 찾기
    @Query(value = "select * from member m where m.user_name=:userName and m.user_phone=:userPhone", nativeQuery = true)
    MemberEntity findByUserNameUserPhone(@Param("userName") String userName, @Param("userPhone") String userPhone);

}
