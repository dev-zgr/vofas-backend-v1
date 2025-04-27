package org.example.vofasbackendv1.data_layer.repositories;

import org.example.vofasbackendv1.data_layer.entities.WebsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends JpaRepository<WebsiteEntity, Long> , PagingAndSortingRepository<WebsiteEntity, Long> {
}
