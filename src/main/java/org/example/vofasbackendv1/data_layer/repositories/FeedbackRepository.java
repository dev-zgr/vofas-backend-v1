package org.example.vofasbackendv1.data_layer.repositories;

import org.example.vofasbackendv1.data_layer.entities.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long>, PagingAndSortingRepository<FeedbackEntity, Long> {

    @Query("SELECT MAX(v.feedbackId) FROM VoiceFeedbackEntity v")
    Long findMaxId();
}
