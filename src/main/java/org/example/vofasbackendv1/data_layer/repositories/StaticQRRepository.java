package org.example.vofasbackendv1.data_layer.repositories;

import jakarta.validation.constraints.NotNull;
import org.example.vofasbackendv1.data_layer.entities.StaticQREntity;
import org.example.vofasbackendv1.data_layer.enums.FeedbackSourceStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaticQRRepository  extends JpaRepository<StaticQREntity, Long>, PagingAndSortingRepository<StaticQREntity, Long> {
    Page<StaticQREntity> findByState(FeedbackSourceStateEnum state, Pageable pageable);

    Optional<StaticQREntity> findByFeedbackSourceId(Long feedbackSourceId);

    Optional<StaticQREntity> findByQrID(@NotNull UUID qrID);
}
