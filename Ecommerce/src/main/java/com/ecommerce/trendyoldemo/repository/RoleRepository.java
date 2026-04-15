package com.ecommerce.trendyoldemo.repository;


import com.ecommerce.trendyoldemo.entity.RoleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role_id) select ?1,id from roles where admin=1", nativeQuery = true)
    @Modifying
    void assignAdminRoles(Long userId);

    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role_id) select ?1,id from roles where customer=1", nativeQuery = true)
    @Modifying
    void assignCustomerRoles(Long userId);

    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role_id) select ?1,id from roles where provider=1", nativeQuery = true)
    @Modifying
    void assignProviderRoles(Long userId);

    @Transactional
    @Query(value = "INSERT INTO user_roles (user_id, role_id) select ?1,id from roles where company=1", nativeQuery = true)
    @Modifying
    void assignCompanyRoles(Long userId);

    @Transactional
    @Modifying
    @Query(value = """
    INSERT INTO user_roles (user_id, role_id)
    SELECT :userId, r.id
    FROM roles r
    WHERE (r.customer = 1 OR r.company = 1)
      AND NOT EXISTS (
          SELECT 1 FROM user_roles ur
          WHERE ur.user_id = :userId AND ur.role_id = r.id
      )
    """, nativeQuery = true)
    void mergeCustomerWithCompanyRoles(Long userId);


    @Transactional
    @Modifying
    @Query(value = """
    INSERT INTO user_roles (user_id, role_id)
    SELECT :userId, r.id
    FROM roles r
    WHERE (r.customer = 1 OR r.provider = 1)
      AND NOT EXISTS (
          SELECT 1 FROM user_roles ur
          WHERE ur.user_id = :userId AND ur.role_id = r.id
      )
    """, nativeQuery = true)
    void mergeCustomerWithProviderRoles(Long userId);

}
