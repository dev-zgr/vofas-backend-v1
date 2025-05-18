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
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>, PagingAndSortingRepository<FeedbackEntity, Long> {

    @Query("SELECT MAX(v.feedbackId) FROM VoiceFeedbackEntity v")
    Long findMaxId();

    @Query("SELECT f FROM FeedbackEntity f WHERE f.feedbackDate BETWEEN :startDate AND :endDate" +
            " AND (:feedbackStatuses IS NULL OR f.feedbackStatus IN :feedbackStatuses)" +
            " AND (:feedbackMethods IS NULL OR f.methodEnum IN :feedbackMethods)" +
            " AND (:sentiments IS NULL OR f.sentiment IN :sentiments)" +
            " AND (:types IS NULL OR f.typeEnum IN :types)")
    Page<FeedbackEntity> findByFeedbackDateBetweenAndFilter(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("feedbackStatuses") String[] feedbackStatuses,
            @Param("feedbackMethods") String[] feedbackMethods,
            @Param("sentiments") String[] sentiments,
            @Param("types") String[] types,
            Pageable pageable
    );

    @Query("SELECT f FROM FeedbackEntity f WHERE " +
            "(:feedbackStatuses IS NULL OR f.feedbackStatus IN :feedbackStatuses) " +
            "AND (:feedbackMethods IS NULL OR f.methodEnum IN :feedbackMethods) " +
            " AND (:sentiments IS NULL OR f.sentiment IN :sentiments)" +
            " AND (:types IS NULL OR f.typeEnum IN :types)")
    Page<FeedbackEntity> findByFilter(
            @Param("feedbackStatuses") String[] feedbackStatuses,
            @Param("feedbackMethods") String[] feedbackMethods,
            @Param("sentiments") String[] sentiments,
            @Param("types") String[] types,
            Pageable pageable
    );

    @Query(value = """
    SELECT
      DATE_SUB(DATE(f.feedback_date), INTERVAL (DATEDIFF(DATE(f.feedback_date), DATE(:startDate)) % :intervalDays) DAY) AS interval_start,
    
      SUM(CASE WHEN f.sentiment = 'POSITIVE' THEN 1 ELSE 0 END) AS positive,
      SUM(CASE WHEN f.sentiment = 'NEUTRAL' THEN 1 ELSE 0 END) AS neutral,
      SUM(CASE WHEN f.sentiment = 'NEGATIVE' THEN 1 ELSE 0 END) AS negative,
    
      SUM(CASE WHEN fs.source_type = 'KIOSK' THEN 1 ELSE 0 END) AS kiosksSource,
      SUM(CASE WHEN fs.source_type = 'WEBSITE' THEN 1 ELSE 0 END) AS websitesSource,
      SUM(CASE WHEN fs.source_type = 'STATIC_QR' THEN 1 ELSE 0 END) AS staticQRSource,
    
      SUM(CASE WHEN f.method = 'KIOSK' THEN 1 ELSE 0 END) AS kiosksMethod,
      SUM(CASE WHEN f.method = 'WEBSITE' THEN 1 ELSE 0 END) AS websitesMethod,
      SUM(CASE WHEN f.method = 'STATIC_QR' THEN 1 ELSE 0 END) AS staticQRMethod,
      SUM(CASE WHEN f.method = 'DYNAMIC_QR' THEN 1 ELSE 0 END) AS dynamicQRMethod,
    
      SUM(CASE WHEN f.type = 'VOICE' THEN 1 ELSE 0 END) AS voiceType,
      SUM(CASE WHEN f.type = 'TEXT' THEN 1 ELSE 0 END) AS textType,
    
      SUM(CASE WHEN f.feedback_status = 'READY' THEN 1 ELSE 0 END) AS validated,
      SUM(CASE WHEN f.feedback_status != 'READY' THEN 1 ELSE 0 END) AS nonValidated
    
    FROM feedback_table f
    LEFT JOIN feedback_source_table fs ON f.feedback_source_id = fs.feedback_source_id
    WHERE f.feedback_date BETWEEN :startDate AND :endDate
    GROUP BY interval_start
    ORDER BY interval_start
    """, nativeQuery = true)
    List<Object[]> findFeedbackAggregatedByInterval(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("intervalDays") int intervalDays
    );
}
