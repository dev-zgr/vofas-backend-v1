package org.example.vofasbackendv1.data_layer.repositories;

import org.example.vofasbackendv1.data_layer.entities.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>, PagingAndSortingRepository<FeedbackEntity, Long> {

    @Query("SELECT MAX(v.feedbackId) FROM VoiceFeedbackEntity v")
    Long findMaxId();

    @Query("SELECT f FROM FeedbackEntity f WHERE f.feedbackDate BETWEEN :startDate AND :endDate" +
            " AND (:feedbackStatuses IS NULL OR f.feedbackStatus IN :feedbackStatuses)" +
            " AND (:feedbackMethods IS NULL OR f.methodEnum IN :feedbackMethods)" +
            " AND (:sentiments IS NULL OR f.sentiment IN :sentiments)")
    Page<FeedbackEntity> findByFeedbackDateBetweenAndFilter(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("feedbackStatuses") String[] feedbackStatuses,
            @Param("feedbackMethods") String[] feedbackMethods,
            @Param("sentiments") String[] sentiments,
            Pageable pageable
    );

    @Query("SELECT f FROM FeedbackEntity f WHERE " +
            "(:feedbackStatuses IS NULL OR f.feedbackStatus IN :feedbackStatuses) " +
            "AND (:feedbackMethods IS NULL OR f.methodEnum IN :feedbackMethods) " +
            "AND (:sentiments IS NULL OR f.sentiment IN :sentiments)")
    Page<FeedbackEntity> findByFilter(
            @Param("feedbackStatuses") String[] feedbackStatuses,
            @Param("feedbackMethods") String[] feedbackMethods,
            @Param("sentiments") String[] sentiments,
            Pageable pageable
    );
}
