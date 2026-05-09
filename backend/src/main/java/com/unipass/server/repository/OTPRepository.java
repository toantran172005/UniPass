package com.unipass.server.repository;

import com.unipass.server.entity.OTPVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTPVerification, Long> {
}
