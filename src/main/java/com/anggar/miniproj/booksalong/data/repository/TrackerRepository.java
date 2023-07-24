package com.anggar.miniproj.booksalong.data.repository;

import com.anggar.miniproj.booksalong.data.entity.Tracker;
import com.anggar.miniproj.booksalong.data.entity.idclass.TrackerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Tracker, TrackerId> {
}
