package org.sleigh.ride.registeruser.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<User,Long> {
}
